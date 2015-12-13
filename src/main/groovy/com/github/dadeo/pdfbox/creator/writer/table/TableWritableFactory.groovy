package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.*
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWritable
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.creator.writer.util.VerticalAlignmentCalculator
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.Cell
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.Table

class TableWritableFactory implements ObjectWritableFactory<Table> {
    ObjectContextFactory objectContextFactory
    CurrentLocationAdjuster<Bordered> currentLocationAdjuster
    ObjectWritableFactoryFactory writerFactoryFactory
    ObjectBoundsCalculator objectBoundsCalculator
    TableColumnWidthCalculator columnWidthCalculator
    DescentMultiplier descentMultiplier
    VerticalAlignmentCalculator verticalAlignmentCalculator

    @Override
    ObjectWritable createFor(DContext pageContext, Table table, ElementDetails previousElementDetails) {
        DContext tableContext = objectContextFactory.createContextFrom(pageContext, table)
        tableContext.verticalAlignment = table.verticalAlignment
        currentLocationAdjuster.adjustFor(tableContext, table, previousElementDetails)

        List<Float> columnWidths = columnWidthCalculator.calculateFor(table, tableContext)

        createTableWriter(tableContext, columnWidths, table)
    }

    protected ObjectWritable createTableWriter(DContext tableContext, List<Float> columnWidths, Table table) {
        List<Cell> cellsToPlace = (List<Cell>) table.contents.clone()
        List<CellWritable> rowCellWritables = []
        List<RowWritable> rowWritables = []
        DContext rowContext = objectContextFactory.createContextFrom(tableContext, null)

        int index = 0
        float lastColumnWidth = 0
        while (cellsToPlace) {
            float currentColumnWidth = columnWidths[index]
            DContext columnContext = objectContextFactory.createContextFrom(rowContext, null)
            objectBoundsCalculator.moveBoundsHorizontally(lastColumnWidth, columnContext)
            objectBoundsCalculator.resizeBoundsToContentWidth(currentColumnWidth, columnContext)
            rowCellWritables << createCellWritable(columnContext, (Cell) cellsToPlace.head(), currentColumnWidth)
            cellsToPlace = cellsToPlace.tail()
            lastColumnWidth += currentColumnWidth
            ++index
            if (index == columnWidths.size()) {
                rowWritables << createRowWritable(rowCellWritables, rowContext)
                rowContext = rowContext.clone()
                objectBoundsCalculator.shrinkBoundsVertically(rowWritables[-1].height, rowContext)
                rowCellWritables.clear()
                index = 0
                lastColumnWidth = 0
            }
        }

        float height = rowWritables.height.sum()

        objectBoundsCalculator.resizeBoundsToHeight(height, tableContext)

        new TableWritable(table, rowWritables, tableContext, new TableElementDetails(containingBounds: tableContext.containingBounds))
    }

    RowWritable createRowWritable(List<CellWritable> cellWritables, DContext rowContext) {
        float rowContentHeight = cellWritables.context.contentsBounds.height.max()
        cellWritables.each { CellWritable cellWritable ->
            float cellWritableHeight = cellWritable.context.contentsBounds.height
            float offset = verticalAlignmentCalculator.calculateOffsetFor(cellWritable.context.verticalAlignment, cellWritableHeight, rowContentHeight)
            if (offset)
                cellWritable.offset(0, -offset)

            objectBoundsCalculator.resizeBoundsToHeight(rowContentHeight, cellWritable.context)
        }

        float rowContainingHeight = cellWritables[0].height
        // they have all been resized so we can look at the first one
        objectBoundsCalculator.resizeBoundsToHeight(rowContainingHeight, rowContext)
        new RowWritable((List<CellWritable>) cellWritables.clone(), rowContext, new RowElementDetails(containingBounds: rowContext.containingBounds))
    }

    protected CellWritable createCellWritable(DContext columnContext, Cell cell, float cellWidth) {
        DContext cellContext = objectContextFactory.createContextFrom(columnContext, cell)
        cellContext.verticalAlignment = cell.verticalAlignment
        DContext cellWorkContext = cellContext.clone()

        ElementDetails elementDetails = null
        List<ObjectWritable> cellWritableContents = cell.contents.collect { DObject object ->
            ObjectWritableFactory<? extends DObject> writerFactory = writerFactoryFactory.createWriter(object)
            ObjectWritable cellChildWritable = writerFactory.createFor(cellWorkContext, object, elementDetails)
            elementDetails = cellChildWritable.elementDetails
            objectBoundsCalculator.shrinkBoundsVertically(cellChildWritable.context.containingBounds.height, cellWorkContext)
            cellChildWritable
        }

        float maxHeight = cellWritableContents ? cellWritableContents.context.containingBounds.height.sum() : 0
        float heightAdjustment = calculateCellHeightAdjustmentFor(cellWritableContents)
        // adjust if cell has border and tallest cell's last writable is for a borderless paragraph
        float height = (cell.borderBottom ? maxHeight - heightAdjustment : maxHeight)

        objectBoundsCalculator.resizeBoundsToHeight(height, cellContext)

        new CellWritable(cell: cell, context: cellContext, contents: cellWritableContents, elementDetails: new CellElementDetails(containingBounds: cellContext.containingBounds))
    }

    float calculateCellHeightAdjustmentFor(List<ObjectWritable> cellWritableContents) {
        if(!cellWritableContents) return 0
        ObjectWritable objectWritable = cellWritableContents[-1]
        switch(objectWritable) {
            case ParagraphWritable:
//                return descentMultiplier.apply(objectWritable.elementDetails.lastLineDescent)
            default:
                return 0
        }
    }

}