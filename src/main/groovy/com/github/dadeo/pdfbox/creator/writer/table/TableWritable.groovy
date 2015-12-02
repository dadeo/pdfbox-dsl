package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Table


class TableWritable implements ObjectWritable {
    BorderDrawer borderDrawer = BootStrap.borderDrawer
    private Table table
    private List<RowWritable> contents
    private DContext context
    private ElementDetails elementDetails

    TableWritable(Table table, List<RowWritable> contents, DContext context, ElementDetails elementDetails) {
        this.table = table
        this.contents = contents
        this.context = context
        this.elementDetails = elementDetails
    }

    @Override
    void write() {
        contents*.write()
        borderDrawer.drawFor(table, context)
    }

    @Override
    ElementDetails getElementDetails() {
        elementDetails
    }

    @Override
    DContext getContext() {
        context
    }

    @Override
    void offset(float x, float y) {
        ObjectWritable.super.offset(x, y)
        contents*.offset(x, y)
    }
}