package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.PreviousElementDetails
import com.github.dadeo.pdfbox.model.Bordered

class AdjacentBordersParagraphAdjustmentRule implements ParagraphAdjustmentRule {

    float calculateAdjustmentFor(DContext paragraphContext, Bordered bordered, PreviousElementDetails previousElementDetails) {
        switch (previousElementDetails) {
            case ParagraphPreviousElementDetails:
                ParagraphPreviousElementDetails details = previousElementDetails as ParagraphPreviousElementDetails
                return details.hasBottomBorder && bordered.borderTop ? 1 : 0
            default:
                return 0
        }
    }

}