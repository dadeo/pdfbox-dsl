package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.DHorizontalRule

class BottomBorderHorizontalAdjustmentRule implements CurrentLocationAdjustmentRule<DHorizontalRule> {
    DescentMultiplier descentMultiplier

    float calculateAdjustmentFor(DContext context, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        float additionalOffsetFromPrevious = 0

        switch (previousElementDetails) {
            case ParagraphElementDetails:
                ParagraphElementDetails details = (ParagraphElementDetails) previousElementDetails
                additionalOffsetFromPrevious = details.hasBottomBorder ? 0 : descentMultiplier.apply(details.lastLineDescent)
                break
        }

        additionalOffsetFromPrevious
    }

}