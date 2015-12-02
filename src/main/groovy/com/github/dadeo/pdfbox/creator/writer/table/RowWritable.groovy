package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails


class RowWritable implements ObjectWritable {
    List<CellWritable> contents
    DContext context
    ElementDetails elementDetails

    RowWritable(List<CellWritable> contents, DContext context, ElementDetails elementDetails) {
        this.contents = contents
        this.context = context
        this.elementDetails = elementDetails
    }

    @Override
    void write() {
        contents.each {
//            float offset = (maxHeight - it.height) / 2
//            it.offset(0, -offset)
            it.write()
        }
    }

    @Override
    void offset(float x, float y) {
//        ObjectWritable.super.offset(x, y)
        contents*.offset(x, y)
    }

    float getHeight() {
        context.containingBounds.height
    }
}