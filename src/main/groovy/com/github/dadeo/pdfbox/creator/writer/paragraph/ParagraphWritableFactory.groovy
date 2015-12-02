package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWritableFactory implements ObjectWritableFactory<DParagraph> {
    ParagraphContentsSizeCalculator contentsSizeCalculator
    ParagraphContextFactory paragraphContextFactory
    BoundedTextBlockFactory boundedTextBlockFactory
    CurrentLocationAdjuster<Bordered> currentLocationAdjuster
    ObjectBoundsCalculator objectBoundsCalculator
    ParagraphElementDetailsFactory elementDetailsFactory

    @Override
    ObjectWritable createFor(DContext pageContext, DParagraph dParagraph, ElementDetails previousElementDetails) {
        // todo: is the following needed? or should boundedTextBlockFactory calculate width from contentsBounds
        float width = contentsSizeCalculator.calculateWidthFor(dParagraph, pageContext.contentsBounds)
        DContext paragraphContext = paragraphContextFactory.createContextFrom(pageContext, dParagraph)
        BoundedTextBlock textBlock = boundedTextBlockFactory.createFrom(paragraphContext, dParagraph, width)
        currentLocationAdjuster.adjustFor(paragraphContext, dParagraph, previousElementDetails)
        float height = contentsSizeCalculator.calculateHeight(dParagraph, textBlock)
        objectBoundsCalculator.calculateActualBounds(paragraphContext, height)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(paragraphContext, dParagraph, textBlock)
        new ParagraphWritable(dParagraph, textBlock, paragraphContext, currentElementDetails)
    }

}