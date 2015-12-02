package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.Bordered

class TopLeftRightBorderParagraphAdjustmentRule implements CurrentLocationAdjustmentRule<Bordered> {
    DescentMultiplier descentMultiplier

    float calculateAdjustmentFor(DContext paragraphContext, Bordered bordered, ElementDetails previousElementDetails) {
        float additionalOffsetFromPrevious = 0

        if (bordered.borderTop || bordered.borderLeft || bordered.borderRight) {
            switch (previousElementDetails) {
                case ParagraphElementDetails:
                    ParagraphElementDetails details = (ParagraphElementDetails) previousElementDetails
                    additionalOffsetFromPrevious = details.hasBottomBorder ? 0 : descentMultiplier.apply(details.lastLineDescent)
                    break
            }
        }

        additionalOffsetFromPrevious
    }

}