package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Cell


class CellWritable implements ObjectWritable {
    BorderDrawer borderDrawer = BootStrap.borderDrawer
    Cell cell
    DContext context
    ElementDetails elementDetails
    List<ObjectWritable> contents

    @Override
    void write() {
        contents.each {
            it.write()
        }

        borderDrawer.drawFor(cell, context)
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