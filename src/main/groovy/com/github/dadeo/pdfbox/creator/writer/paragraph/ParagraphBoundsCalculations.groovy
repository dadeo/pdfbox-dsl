package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph


class ParagraphBoundsCalculations {

    DBounds calculateContainingBounds(DContext paragraphContext, DParagraph paragraph, BoundedTextBlock textBlock) {
        float marginAdjustment = paragraph.marginTop + paragraph.marginBottom
        float borderAdjustment = paragraph.borderTop + paragraph.borderBottom
        float paddingAdjustment = paragraph.paddingTop + paragraph.paddingBottom
        float descentOffset = paragraph.borderBottom ? textBlock.lastLineDescent : 0

        new DBounds(paragraphContext.currentLocation.y,
                    paragraphContext.parent.contentsBounds.right,
                    (float) (paragraphContext.currentLocation.y - textBlock.height - marginAdjustment - borderAdjustment - paddingAdjustment + descentOffset),
                    paragraphContext.currentLocation.x)
    }

    DBounds calculateBorderBounds(DContext paragraphContext, DParagraph paragraph) {
        paragraphContext.containingBounds
                        .offset(paragraph.marginOffsets)
    }

    DBounds calculateContentsBounds(DContext paragraphContext, DParagraph paragraph) {
        paragraphContext.borderBounds
                        .offset(paragraph.borderTextOffsets)
                        .offset(paragraph.paddingOffsets)
    }
}