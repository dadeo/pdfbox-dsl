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
    private DContext context
    private ElementDetails elementDetails

    ParagraphWritable(DParagraph paragraph, BoundedTextBlock textBlock, DContext context, ElementDetails elementDetails) {
        this.dParagraph = paragraph
        this.textBlock = textBlock
        this.context = context
        this.elementDetails = elementDetails
    }

    @Override
    void write() {
        boundedTextBlockWriter.write(textBlock, context)
        borderDrawer.drawFor(dParagraph, context)
    }

    @Override
    ElementDetails getElementDetails() {
        elementDetails
    }

    @Override
    DContext getContext() {
        context
    }
}