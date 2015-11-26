package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphBoundsCalculator {
    ParagraphBoundsCalculations calculations = new ParagraphBoundsCalculations()

    void addCalculationsTo(DContext paragraphContext, DParagraph paragraph, BoundedTextBlock textBlock) {
        paragraphContext.containingBounds = calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock)
        paragraphContext.borderBounds = calculations.calculateBorderBounds(paragraphContext, paragraph)
        paragraphContext.contentsBounds = calculations.calculateContentsBounds(paragraphContext, paragraph)
    }

}