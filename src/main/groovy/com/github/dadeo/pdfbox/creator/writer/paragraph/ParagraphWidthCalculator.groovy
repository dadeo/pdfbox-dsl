package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph


class ParagraphWidthCalculator {

    float calculateFor(DParagraph paragraph, DBounds areaBounds) {
        float right = areaBounds.right - paragraph.marginRight - paragraph.borderRight - paragraph.paddingRight
        float left = areaBounds.left + paragraph.marginLeft + paragraph.borderLeft + paragraph.paddingLeft
        right - left
    }

}