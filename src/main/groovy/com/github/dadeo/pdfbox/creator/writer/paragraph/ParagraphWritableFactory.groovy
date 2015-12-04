package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWritableFactory implements ObjectWritableFactory<DParagraph> {
    ObjectContentsWidthCalculator objectContentsWidthCalculator = new ObjectContentsWidthCalculator()
    ParagraphContextFactory paragraphContextFactory = new ParagraphContextFactory()
    BoundedTextBlockFactory boundedTextBlockFactory = new BoundedTextBlockFactory()
    CurrentLocationAdjuster<Bordered> currentLocationAdjuster = BootStrap.paragraphCurrentLocationAdjuster
    ObjectBoundsCalculator objectBoundsCalculator = new ObjectBoundsCalculator()
    ParagraphElementDetailsFactory elementDetailsFactory = BootStrap.paragraphElementDetailsFactory

    @Override
    ObjectWritable createFor(DContext pageContext, DParagraph dParagraph, ElementDetails previousElementDetails) {
        float width = objectContentsWidthCalculator.calculateFor(dParagraph, pageContext.contentsBounds)
        DContext paragraphContext = paragraphContextFactory.createContextFrom(pageContext, dParagraph)
        BoundedTextBlock textBlock = boundedTextBlockFactory.createFrom(paragraphContext, dParagraph, width)
        currentLocationAdjuster.adjustFor(paragraphContext, dParagraph, previousElementDetails)
        objectBoundsCalculator.calculateActualBounds(paragraphContext, textBlock.height)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(paragraphContext, dParagraph, textBlock)
        new ParagraphWritable(dParagraph, textBlock, paragraphContext, currentElementDetails)
    }

}