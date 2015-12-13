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
package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Justification
import spock.lang.Specification


class TextBlockLineWriterFactoryTest extends Specification {
    private TextBlockLineWriterFactory factory = BootStrap.textBlockLineWriterFactory
    private DContext context = new DContext()

    def "creates writer for left justified text"() {
        given:
        context.textJustification = Justification.LEFT

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.leftJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
    }

    def "creates writer for right justified text"() {
        given:
        context.textJustification = Justification.RIGHT

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.rightJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof RightJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof RightJustifiedTextBlockCurrentLocationPositioner
    }

    def "creates writer for center justified text"() {
        given:
        context.textJustification = Justification.CENTER

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.centerJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof CenterJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof CenterJustifiedTextBlockCurrentLocationPositioner
    }

    def "defaults to writer for left justified text when not specified"() {
        given:
        context.textJustification = null

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.leftJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
    }

}