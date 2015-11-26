package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.PreviousElementDetails
import com.github.dadeo.pdfbox.model.Bordered

class TopLeftRightBorderParagraphAdjustmentRule implements ParagraphAdjustmentRule {

    float calculateAdjustmentFor(DContext paragraphContext, Bordered bordered, PreviousElementDetails previousElementDetails) {
        float additionalOffsetFromPrevious = 0

        if (bordered.borderTop || bordered.borderLeft || bordered.borderRight) {
            switch (previousElementDetails) {
                case ParagraphPreviousElementDetails:
                    ParagraphPreviousElementDetails details = (ParagraphPreviousElementDetails) previousElementDetails
                    additionalOffsetFromPrevious = details.hasBottomBorder ? 0 : details.lastLineDescent
                    break
            }
        }

        additionalOffsetFromPrevious
    }

}