package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Table


class TableColumnWidthCalculator {

    List<Float> calculateFor(Table table, DContext tableContext) {
        Float ratioSum = table.columnRatios.sum()
        float totalContentWidth = tableContext.contentsBounds.width

        List<Float> columnWidths = table.columnRatios.collect { (float) (totalContentWidth / ratioSum * it) }
        columnWidths
    }

}