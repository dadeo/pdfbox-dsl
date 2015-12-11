package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.Margined
import com.github.dadeo.pdfbox.model.Padded

class ObjectBoundsCalculator {

    void calculateMaxBounds(DObject object, DContext context) {
        context.borderBounds = context.containingBounds

        if (object instanceof Margined)
            context.borderBounds = context.borderBounds
                                          .offset(object.marginOffsets)

        context.contentsBounds = context.borderBounds

        if (object instanceof Bordered)
            context.contentsBounds = context.contentsBounds
                                            .offset(object.borderTextOffsets)

        if (object instanceof Padded)
            context.contentsBounds = context.contentsBounds
                                            .offset(object.paddingOffsets)
    }

    void resizeBoundsToHeight(float height, DContext context) {
        float oldHeight = context.contentsBounds.height
        float adjustment = oldHeight - height
        context.containingBounds = context.containingBounds.offset(0, 0, adjustment, 0)
        context.borderBounds = context.borderBounds.offset(0, 0, adjustment, 0)
        context.contentsBounds = context.contentsBounds.offset(0, 0, adjustment, 0)
    }
}