package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Bordered

class AdjacentBordersParagraphAdjustmentRule implements ParagraphAdjustmentRule {

    float calculateAdjustmentFor(DContext paragraphContext, Bordered bordered, ElementDetails previousElementDetails) {
        switch (previousElementDetails) {
            case ParagraphElementDetails:
                ParagraphElementDetails details = previousElementDetails as ParagraphElementDetails
                return details.hasBottomBorder && bordered.borderTop ? 1 : 0
            default:
                return 0
        }
    }

}