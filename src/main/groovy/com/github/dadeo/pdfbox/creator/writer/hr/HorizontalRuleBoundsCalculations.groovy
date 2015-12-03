package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleBoundsCalculations {

    DBounds calculateContainingBounds(DContext context, DHorizontalRule horizontalRule) {
        float marginAdjustment = horizontalRule.marginTop + horizontalRule.marginBottom
        float borderAdjustment = horizontalRule.borderTop + horizontalRule.borderBottom
        float paddingAdjustment = horizontalRule.paddingTop + horizontalRule.paddingBottom

        new DBounds(context.containingBounds.top,
                    context.containingBounds.right,
                    (float) (context.containingBounds.top - horizontalRule.thickness - marginAdjustment - borderAdjustment - paddingAdjustment),
                    context.containingBounds.left)
    }

    DBounds calculateBorderBounds(DContext context, DHorizontalRule horizontalRule) {
        context.containingBounds
               .offset(horizontalRule.marginOffsets)
    }

    DBounds calculateContentsBounds(DContext context, DHorizontalRule horizontalRule) {
        context.borderBounds
               .offset(horizontalRule.borderTextOffsets)
               .offset(horizontalRule.paddingOffsets)

    }

}