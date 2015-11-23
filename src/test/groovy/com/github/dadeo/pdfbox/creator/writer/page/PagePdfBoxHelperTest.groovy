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