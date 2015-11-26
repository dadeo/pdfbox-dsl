package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.PreviousElementDetails
import com.github.dadeo.pdfbox.model.Bordered

class ParagraphCurrentLocationAdjuster {
    List<ParagraphAdjustmentRule> adjustmentRules = [
        new TopLeftRightBorderParagraphAdjustmentRule(),
        new AdjacentBordersParagraphAdjustmentRule()
    ]

    void adjustFor(DContext paragraphContext, Bordered bordered, PreviousElementDetails previousElementDetails) {
        float offset = adjustmentRules.inject(0f) { accum, rule -> accum + rule.calculateAdjustmentFor(paragraphContext, bordered, previousElementDetails) }

        paragraphContext.currentLocation = paragraphContext.currentLocation.offsetY(offset)
    }

}