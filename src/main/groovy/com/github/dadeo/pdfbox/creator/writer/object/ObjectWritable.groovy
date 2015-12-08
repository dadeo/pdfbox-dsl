package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails

trait ObjectWritable {

    abstract void write()

    abstract ElementDetails getElementDetails()

    abstract DContext getContext()

    void offset(float x, float y) {
        DContext context = getContext()
        context.containingBounds = context.containingBounds.offset(y, x, y, x)
        context.borderBounds = context.borderBounds.offset(y, x, y, x)
        context.contentsBounds = context.contentsBounds.offset(y, x, y, x)
    }
}