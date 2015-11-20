package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage
import com.github.dadeo.pdfbox.model.DParagraph
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

class PageWriter {
    static final float ONE_INCH = 72f

    ParagraphWriter paragraphWriter = new ParagraphWriter()

    void write(DContext documentContext, DPage dPage) {
        PDPage page = new PDPage()
        PDPageContentStream contentStream = new PDPageContentStream(documentContext.pdDocument, page)

        // todo: make configurable
        DBounds pageBounds = new DBounds((float) (10 * ONE_INCH), (float) (7.5 * ONE_INCH), ONE_INCH, ONE_INCH)

        DContext pageContext = documentContext.cloneNotNull(bounds: pageBounds,
                                                            currentLocation: pageBounds.leftTop(),
                                                            font: dPage.font,
                                                            pdContentStream: contentStream,
                                                            writer: new DWriter(contentStream: contentStream))

        // todo: should be looping over DObject (not DParagraph) and resolving writer from a factory.

        dPage.contents.inject((PreviousElementDetails) null) { PreviousElementDetails previousElementDetails, DParagraph dParagraph ->
            paragraphWriter.write(pageContext, dParagraph, previousElementDetails)
        }

        contentStream.close()
        documentContext.pdDocument.addPage(page)
    }
}