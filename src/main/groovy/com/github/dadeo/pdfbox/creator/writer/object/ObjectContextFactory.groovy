package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DObject

class ObjectContextFactory {
    ObjectBoundsCalculator objectBoundsCalculator

    DContext createContextFrom(DContext parentContext, DObject object) {
        DContext childContext = parentContext.clone()
        childContext.parent = parentContext
        childContext.containingBounds = parentContext.contentsBounds
        objectBoundsCalculator.calculateMaxBounds(object, childContext)
        childContext
    }

}