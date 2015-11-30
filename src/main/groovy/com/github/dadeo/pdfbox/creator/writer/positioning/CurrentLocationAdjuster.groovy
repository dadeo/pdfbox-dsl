package com.github.dadeo.pdfbox.creator.writer.positioning

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails

class CurrentLocationAdjuster<T> {
    List<CurrentLocationAdjustmentRule> adjustmentRules = []

    void adjustFor(DContext context, T currentObject, ElementDetails previousElementDetails) {
        float offset = adjustmentRules.inject(0f) { accum, rule -> accum + rule.calculateAdjustmentFor(context, currentObject, previousElementDetails) }

        context.currentLocation = context.currentLocation.offsetY(offset)
    }

}