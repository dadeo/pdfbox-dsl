package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.*
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.creator.writer.util.VerticalAlignmentCalculator
import com.github.dadeo.pdfbox.model.*

class TableWritableFactory implements ObjectWritableFactory<Table> {
    ObjectContextFactory objectContextFactory
    CurrentLocationAdjuster<Bordered> currentLocationAdjuster
    ObjectWritableFactoryFactory writerFactoryFactory
    ObjectBoundsCalculator objectBoundsCalculator
    DescentMultiplier descentMultiplier
    VerticalAlignmentCalculator verticalAlignmentCalculator

    @Override
    ObjectWritable createFor(DContext pageContext, Table table, ElementDetails previousElementDetails) {
        DContext tableContext = objectContextFactory.createContextFrom(pageContext, table)
        tableContext.verticalAlignment = table.verticalAlignment
        currentLocationAdjuster.adjustFor(tableContext, table, previousElementDetails)

        List<Float> columnWidths = calculateColumnWidths(table, tableContext)

        createTableWriter(tableContext, columnWidths, table)
    }

    protected ObjectWritable createTableWriter(DContext tableContext, List<Float> columnWidths, Table table) {
        List<Cell> cellsToPlace = (List<Cell>) table.contents.clone()
        List<CellWritable> rowCellWritables = []
        List<RowWritable> rowWritables = []
        DContext rowContext = objectContextFactory.createContextFrom(tableContext, null)

        int index = 0
        float widthOffset = 0
        while (cellsToPlace) {
            float width = columnWidths[index]
            DContext columnContext = objectContextFactory.createContextFrom(rowContext, null)
            moveBoundsByContentWidthOf(widthOffset, columnContext)
            adjustBoundsForContentWidthOf(width, columnContext)
            rowCellWritables << createCellWritable(columnContext, (Cell) cellsToPlace.head(), width)
            cellsToPlace = cellsToPlace.tail()
            widthOffset += width
            ++index
            if (index == columnWidths.size()) {
                rowWritables << createRowWritable(rowCellWritables, rowContext)
                rowContext = rowContext.clone()
                moveBoundsDown(rowContext, rowWritables[-1])
                rowCellWritables.clear()
                index = 0
                widthOffset = 0
            }
        }

        float height = rowWritables.height.sum()

        objectBoundsCalculator.calculateActualBounds(tableContext, height)

        new TableWritable(table, rowWritables, tableContext, new TableElementDetails(containingBounds: tableContext.containingBounds))
    }

    RowWritable createRowWritable(List<CellWritable> cellWritables, DContext rowContext) {
        float rowContentHeight = cellWritables.context.contentsBounds.height.max()
        cellWritables.each { CellWritable cellWritable ->
            // vertical justification -- middle
            float cellWritableHeight = cellWritable.context.contentsBounds.height
            float offset = verticalAlignmentCalculator.calculateOffsetFor(cellWritable.context.verticalAlignment, cellWritableHeight, rowContentHeight)
            if (offset)
                cellWritable.offset(0, -offset)

            objectBoundsCalculator.calculateActualBounds(cellWritable.context, rowContentHeight)
        }

        float rowContainingHeight = cellWritables[0].height
        // they have all been resized so we can look at the first one
        objectBoundsCalculator.calculateActualBounds(rowContext, rowContainingHeight)
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
            cellWorkContext.contentsBounds = cellWorkContext.contentsBounds.offset((float) (elementDetails.containingBounds.bottom - cellWorkContext.contentsBounds.top + 1), 0, 0, 0)
            cellChildWritable
        }

        float maxHeight = cellWritableContents ? cellContext.contentsBounds.top - cellWritableContents.elementDetails[-1].containingBounds.bottom + 1 : 0
        float lastLineDescent = 0
        // adjust if cell has border and tallest cell's last writable is for a borderless paragraph
        float height = (cell.borderBottom ? maxHeight - descentMultiplier.apply(lastLineDescent) : maxHeight)

        objectBoundsCalculator.calculateActualBounds(cellContext, height)

        new CellWritable(cell: cell, context: cellContext, contents: cellWritableContents, elementDetails: new CellElementDetails(containingBounds: cellContext.containingBounds))
    }

    protected void adjustBoundsForContentWidthOf(float width, DContext context) {
        float currentWidth = context.contentsBounds.width
        float adjustment = currentWidth - width
        context.containingBounds = context.containingBounds.offset(0, -adjustment, 0, 0)
        context.borderBounds = context.borderBounds.offset(0, -adjustment, 0, 0)
        context.contentsBounds = context.contentsBounds.offset(0, -adjustment, 0, 0)
    }

    protected void moveBoundsByContentWidthOf(float width, DContext context) {
        context.containingBounds = context.containingBounds.offset(0, 0, 0, width)
        context.borderBounds = context.borderBounds.offset(0, 0, 0, width)
        context.contentsBounds = context.contentsBounds.offset(0, 0, 0, width)
    }

    protected void moveBoundsDown(DContext context, RowWritable rowWritable) {
        float height = rowWritable.elementDetails.containingBounds.height
        context.containingBounds = context.containingBounds.offset(-height, 0, 0, 0)
        context.borderBounds = context.borderBounds.offset(-height, 0, 0, 0)
        context.contentsBounds = context.contentsBounds.offset(-height, 0, 0, 0)
    }

    protected void resizeHeight(DContext context, float height) {
        float currentHeight = context.contentsBounds.height
        float adjustment = currentHeight - height
        context.containingBounds = context.containingBounds.offset(0, 0, adjustment, 0)
        context.borderBounds = context.borderBounds.offset(0, 0, adjustment, 0)
        context.contentsBounds = context.contentsBounds.offset(0, 0, adjustment, 0)
    }

    protected List<Float> calculateColumnWidths(Table table, DContext tableContext) {
        Float ratioSum = table.columnRatios.sum()
        float totalContentWidth = tableContext.contentsBounds.width

        List<Float> columnWidths = table.columnRatios.collect { (float) (totalContentWidth / ratioSum * it) }
        columnWidths
    }

}