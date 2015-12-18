/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactoryFactory
import com.github.dadeo.pdfbox.creator.writer.object.PositionType
import com.github.dadeo.pdfbox.model.PdfComponent
import com.github.dadeo.pdfbox.model.Page
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class PageContentsWriterTest extends Specification {
    private DContext pageContext = Mock(DContext)
    private PageContentsCurrentLocationAdjuster currentLocationAdjuster = Mock(PageContentsCurrentLocationAdjuster)
    private ObjectWritableFactoryFactory factoryFactory = Mock(ObjectWritableFactoryFactory)
    private PageContentsWriter writer = new PageContentsWriter(writableFactoryFactory: factoryFactory, currentLocationAdjuster: currentLocationAdjuster)
    private Page dPage = new Page()

    def "passes null in to first child writer"() {
        PdfComponent contents1 = new PdfComponent() {}
        ObjectWritable objectWritable1 = Mock(ObjectWritable)
        ObjectWritableFactory factory1 = Mock(ObjectWritableFactory)

        given:

        dPage.contents = [contents1]

        when:


        writer.writeContents(dPage, pageContext)

        then:

        1 * factoryFactory.createWriter(contents1) >> factory1
        1 * factory1.createFor(sameInstance(pageContext), contents1) >> objectWritable1
        1 * objectWritable1.write()
        0 * _
    }

    def "passes result of previous content writer in to next content writer"() {
        PdfComponent contents1 = new PdfComponent() {}
        PdfComponent contents2 = new PdfComponent() {}
        PdfComponent contents3 = new PdfComponent() {}
        ObjectWritable objectWritable1 = Mock(ObjectWritable)
        ObjectWritable objectWritable2 = Mock(ObjectWritable)
        ObjectWritable objectWritable3 = Mock(ObjectWritable)
        ObjectWritableFactory factory1 = Mock(ObjectWritableFactory)
        ObjectWritableFactory factory2 = Mock(ObjectWritableFactory)
        ObjectWritableFactory factory3 = Mock(ObjectWritableFactory)
        DContext childContext1 = Mock(DContext)
        DContext childContext2 = Mock(DContext)

        given:

        dPage.contents = [contents1, contents2, contents3]

        when:

        writer.writeContents(dPage, pageContext)

        then:

        1 * factoryFactory.createWriter(sameInstance(contents1)) >> factory1
        1 * factory1.createFor(sameInstance(pageContext), contents1) >> objectWritable1
        1 * objectWritable1.write()
        1 * objectWritable1.getPositionType() >> PositionType.RELATIVE
        1 * objectWritable1.context >> childContext1

        1 * factoryFactory.createWriter(sameInstance(contents2)) >> factory2
        1 * currentLocationAdjuster.adjust(pageContext, childContext1)
        1 * factory2.createFor(sameInstance(pageContext), contents2) >> objectWritable2
        1 * objectWritable2.write()
        1 * objectWritable2.getPositionType() >> PositionType.RELATIVE
        1 * objectWritable2.context >> childContext2

        1 * factoryFactory.createWriter(sameInstance(contents3)) >> factory3
        1 * currentLocationAdjuster.adjust(pageContext, childContext2)
        1 * factory3.createFor(sameInstance(pageContext), contents3) >> objectWritable3
        1 * objectWritable3.write()

        0 * _
    }

    def "location isn't adjusted for absolutely positioned elements"() {
        PdfComponent contents1 = new PdfComponent() {}
        PdfComponent contents2 = new PdfComponent() {}
        PdfComponent contents3 = new PdfComponent() {}
        ObjectWritable objectWritable1 = Mock(ObjectWritable)
        ObjectWritable objectWritable2 = Mock(ObjectWritable)
        ObjectWritable objectWritable3 = Mock(ObjectWritable)
        ObjectWritableFactory factory1 = Mock(ObjectWritableFactory)
        ObjectWritableFactory factory2 = Mock(ObjectWritableFactory)
        ObjectWritableFactory factory3 = Mock(ObjectWritableFactory)
        DContext childContext1 = Mock(DContext)

        given:

        dPage.contents = [contents1, contents2, contents3]

        when:

        writer.writeContents(dPage, pageContext)

        then:

        1 * factoryFactory.createWriter(sameInstance(contents1)) >> factory1
        1 * factory1.createFor(sameInstance(pageContext), contents1) >> objectWritable1
        1 * objectWritable1.write()
        1 * objectWritable1.getPositionType() >> PositionType.RELATIVE
        1 * objectWritable1.context >> childContext1

        1 * factoryFactory.createWriter(sameInstance(contents2)) >> factory2
        1 * currentLocationAdjuster.adjust(pageContext, childContext1)
        1 * factory2.createFor(sameInstance(pageContext), contents2) >> objectWritable2
        1 * objectWritable2.write()
        1 * objectWritable2.getPositionType() >> PositionType.ABSOlUTE

        1 * factoryFactory.createWriter(sameInstance(contents3)) >> factory3
        1 * factory3.createFor(sameInstance(pageContext), contents3) >> objectWritable3
        1 * objectWritable3.write()

        0 * _
    }

}