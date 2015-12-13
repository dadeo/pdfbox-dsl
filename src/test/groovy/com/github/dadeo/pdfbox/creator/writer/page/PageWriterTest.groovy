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
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class PageWriterTest extends Specification {
    private PageWriter pageWriter
    private DContext pageContext = new DContext()
    private PageContextFactory pageContextFactory = Mock(PageContextFactory)
    private PagePdfBoxHelper pagePdfBoxHelper = Mock(PagePdfBoxHelper)
    private PageBoundsCalculator pageBoundsCalculator = Mock(PageBoundsCalculator)
    private PageContentsWriter pageContentsWriter = Mock(PageContentsWriter)
    private BorderDrawer pageBorderDrawer = Mock(BorderDrawer)

    def setup() {
        pageWriter = new PageWriter(
            pageContextFactory: pageContextFactory,
            pagePdfBoxHelper: pagePdfBoxHelper,
            pageBoundsCalculator: pageBoundsCalculator,
            pageContentsWriter: pageContentsWriter,
            pageBorderDrawer: pageBorderDrawer
        )
    }

    def "pageWriter write"() {
        given:
        DContext documentContext = new DContext()
        DPage dPage = new DPage()

        when:
        pageWriter.write(documentContext, dPage)

        then:
        1 * pageContextFactory.createContextFrom(sameInstance(documentContext), dPage) >> { pageContext }
        1 * pagePdfBoxHelper.initializePageObjects(sameInstance(pageContext))
        1 * pageBoundsCalculator.addCalculationsTo(sameInstance(pageContext), dPage)
        1 * pageContentsWriter.writeContents(dPage, sameInstance(pageContext))
        1 * pageBorderDrawer.drawFor(dPage, sameInstance(pageContext))
        1 * pagePdfBoxHelper.close(sameInstance(pageContext))
        0 * _
    }

}