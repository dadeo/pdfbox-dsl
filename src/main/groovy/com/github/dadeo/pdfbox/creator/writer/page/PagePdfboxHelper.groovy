package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

class PagePdfBoxHelper {

    void initializePageObjects(DContext pageContext) {
        PDPage page = new PDPage()

        PDDocument document = pageContext.pdDocument
        document.addPage(page)

        PDPageContentStream contentStream = new PDPageContentStream(document, page)

        pageContext.pdPage = page
        pageContext.pdContentStream = contentStream
        pageContext.writer = new DWriter(contentStream: contentStream)
    }

    void close(DContext pageContext) {
        pageContext.pdContentStream.close()
    }

}