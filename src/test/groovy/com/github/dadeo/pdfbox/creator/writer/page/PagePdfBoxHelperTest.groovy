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
import com.github.dadeo.pdfbox.creator.writer.DWriter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import spock.lang.Specification

class PagePdfBoxHelperTest extends Specification {
    private PagePdfBoxHelper pagePdfBoxHelper = new PagePdfBoxHelper()
    private PDDocument pdDocument = new PDDocument()

    void cleanup() {
        pdDocument.close()
    }

    def "initializePageObjects creates page and adds to document"() {
        given:

        DContext pageContext = new DContext(pdDocument: pdDocument)

        when:

        pagePdfBoxHelper.initializePageObjects(pageContext)

        then:

        pageContext.pdDocument.numberOfPages == 1
        pageContext.pdPage.COSObject.is pageContext.pdDocument.pages[0].COSObject
    }

    def "initializePageObjects creates pdContentStream"() {
        given:

        DContext pageContext = new DContext(pdDocument: pdDocument)

        when:

        pagePdfBoxHelper.initializePageObjects(pageContext)

        then:

        pageContext.pdContentStream instanceof PDPageContentStream
        pageContext.pdContentStream.@document.is pageContext.pdDocument
    }

    def "initializePageObjects creates writer for pdContentStream"() {
        given:

        DContext pageContext = new DContext(pdDocument: pdDocument)

        when:

        pagePdfBoxHelper.initializePageObjects(pageContext)

        then:

        pageContext.writer instanceof DWriter
        pageContext.writer.contentStream.is pageContext.pdContentStream
    }

    def "close closes page content stream"() {
        given:

        OutputStream outputStream = Mock(OutputStream)
        DContext pageContext = new DContext(pdDocument: pdDocument)
        pagePdfBoxHelper.initializePageObjects(pageContext)
        pageContext.pdContentStream.@output = outputStream

        when:

        pagePdfBoxHelper.close(pageContext)

        then:

        1 * outputStream.close()
    }
}