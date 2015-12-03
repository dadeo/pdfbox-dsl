package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext

class ObjectContextFactory {

    DContext createContextFrom(DContext parentContext) {
        DContext childContext = parentContext.clone()
        childContext.parent = parentContext
        childContext.containingBounds = parentContext.contentsBounds
        childContext.borderBounds = parentContext.contentsBounds
        childContext.contentsBounds = parentContext.contentsBounds
        childContext
    }

}