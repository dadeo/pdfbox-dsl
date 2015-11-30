package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.PageObjectWriter
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWriter implements PageObjectWriter<DParagraph> {
    ParagraphWidthCalculator paragraphWidthCalculator = new ParagraphWidthCalculator()
    ParagraphContextFactory paragraphContextFactory = new ParagraphContextFactory()
    BoundedTextBlockFactory boundedTextBlockFactory = new BoundedTextBlockFactory()
    ParagraphCurrentLocationAdjuster currentLocationAdjuster = new ParagraphCurrentLocationAdjuster()
    ParagraphBoundsCalculator paragraphBoundsCalculator = new ParagraphBoundsCalculator()
    BoundedTextBlockWriter boundedTextBlockWriter = new BoundedTextBlockWriter()
    BorderDrawer borderDrawer = new BorderDrawer()
    ParagraphElementDetailsFactory paragraphElementDetailsFactory = new ParagraphElementDetailsFactory()

    @Override
    ElementDetails write(DContext pageContext, DParagraph dParagraph, ElementDetails previousElementDetails) {
        float width = paragraphWidthCalculator.calculateFor(dParagraph, pageContext.contentsBounds)
        DContext paragraphContext = paragraphContextFactory.createContextFrom(pageContext, dParagraph)
        BoundedTextBlock textBlock = boundedTextBlockFactory.createFrom(paragraphContext, dParagraph, width)
        currentLocationAdjuster.adjustFor(paragraphContext, dParagraph, previousElementDetails)
        paragraphBoundsCalculator.addCalculationsTo(paragraphContext, dParagraph, textBlock)
        boundedTextBlockWriter.write(textBlock, paragraphContext)
        borderDrawer.drawFor(dParagraph, paragraphContext)
        paragraphElementDetailsFactory.createFor(paragraphContext, dParagraph, textBlock)
    }

}