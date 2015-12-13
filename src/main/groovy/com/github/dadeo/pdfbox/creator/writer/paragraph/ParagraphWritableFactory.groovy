package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWritableFactory implements ObjectWritableFactory<DParagraph> {
    ParagraphContentsDimensionsCalculator contentsDimensionsCalculator
    ParagraphContextFactory paragraphContextFactory
    BoundedTextBlockFactory boundedTextBlockFactory
    ObjectBoundsCalculator objectBoundsCalculator
    ParagraphElementDetailsFactory elementDetailsFactory

    @Override
    ObjectWritable createFor(DContext pageContext, DParagraph dParagraph, ElementDetails previousElementDetails) {
        // todo: is the following needed? or should boundedTextBlockFactory calculate width from contentsBounds
        float width = contentsDimensionsCalculator.calculateWidthFor(dParagraph, pageContext.contentsBounds)
        DContext paragraphContext = paragraphContextFactory.createContextFrom(pageContext, dParagraph)
        BoundedTextBlock textBlock = boundedTextBlockFactory.createFrom(paragraphContext, dParagraph, width)
        float height = contentsDimensionsCalculator.calculateHeightFor(dParagraph, textBlock)
        objectBoundsCalculator.resizeBoundsToHeight(height, paragraphContext)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(paragraphContext, dParagraph, textBlock)
        new ParagraphWritable(dParagraph, textBlock, paragraphContext, currentElementDetails)
    }

}