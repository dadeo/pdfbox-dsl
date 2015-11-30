package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleBoundsCalculator {
    HorizontalRuleBoundsCalculations calculations = new HorizontalRuleBoundsCalculations()

    void addCalculationsTo(DContext context, DHorizontalRule horizontalRule) {
        context.containingBounds = calculations.calculateContainingBounds(context, horizontalRule)
        context.borderBounds = calculations.calculateBorderBounds(context, horizontalRule)
        context.contentsBounds = calculations.calculateContentsBounds(context, horizontalRule)
    }

}