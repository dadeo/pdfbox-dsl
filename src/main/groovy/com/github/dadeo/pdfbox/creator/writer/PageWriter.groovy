package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage
import com.github.dadeo.pdfbox.model.DParagraph
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

class PageWriter {
    static final float ONE_INCH = 72f

    void write(DContext documentContext, DPage dPage) {
        PDPage page = new PDPage()
        PDPageContentStream contentStream = new PDPageContentStream(documentContext.pdDocument, page)

        // todo: make configurable
        DBounds pageBounds = new DBounds((float) (10 * ONE_INCH), (float) (7.5 * ONE_INCH), ONE_INCH, ONE_INCH)

        DContext pageContext = documentContext.cloneNotNull(bounds: pageBounds,
                                                            currentLocation: pageBounds.leftTop(),
                                                            font: dPage.font,
                                                            pdContentStream: contentStream)

        // todo: should be looping over DObject (not DParagraph) and resolving writer from a factory.
        dPage.contents.each { DParagraph dParagraph ->
            new ParagraphWriter().write(pageContext, dParagraph)
        }

//        new DWriter(contentStream: contentStream).drawRectangle(pageBounds)

        contentStream.close()
        documentContext.pdDocument.addPage(page)
    }
}