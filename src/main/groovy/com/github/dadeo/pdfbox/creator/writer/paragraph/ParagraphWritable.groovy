package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWritable implements ObjectWritable {
    private BoundedTextBlockWriter boundedTextBlockWriter = BootStrap.boundedTextBlockWriter
    private BorderDrawer borderDrawer = BootStrap.borderDrawer
    private DParagraph dParagraph
    private BoundedTextBlock textBlock
    private DContext paragraphContext
    private ElementDetails elementDetails

    ParagraphWritable(DParagraph paragraph, BoundedTextBlock textBlock, DContext paragraphContext, ElementDetails elementDetails) {
        this.dParagraph = paragraph
        this.textBlock = textBlock
        this.paragraphContext = paragraphContext
        this.elementDetails = elementDetails
    }

    @Override
    void write() {
        boundedTextBlockWriter.write(textBlock, paragraphContext)
        borderDrawer.drawFor(dParagraph, paragraphContext)
    }

    @Override
    ElementDetails getElementDetails() {
        elementDetails
    }
}