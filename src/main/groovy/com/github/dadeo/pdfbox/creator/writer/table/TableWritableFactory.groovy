package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.*
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.Cell
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.Table

class TableWritableFactory implements ObjectWritableFactory<Table> {
    ObjectContextFactory objectContextFactory
    CurrentLocationAdjuster<Bordered> currentLocationAdjuster
    ObjectWritableFactoryFactory writerFactoryFactory
    ObjectBoundsCalculator objectBoundsCalculator
    DescentMultiplier descentMultiplier

    @Override
    ObjectWritable createFor(DContext pageContext, Table table, ElementDetails previousElementDetails) {
        DContext tableContext = objectContextFactory.createContextFrom(pageContext, table)
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
            float offset = (rowContentHeight - cellWritable.context.contentsBounds.height) / 2
            cellWritable.offset(0, -offset)

            resizeHeight(cellWritable.context, rowContentHeight)
        }

        float rowContainingHeight = cellWritables[0].height + 1
        objectBoundsCalculator.calculateActualBounds(rowContext, rowContainingHeight)
        new RowWritable((List<CellWritable>) cellWritables.clone(), rowContext, new RowElementDetails(containingBounds: rowContext.containingBounds))
    }

    protected CellWritable createCellWritable(DContext columnContext, Cell cell, float cellWidth) {
        DContext cellContext = objectContextFactory.createContextFrom(columnContext, cell)
        DContext cellWorkContext = cellContext.clone()

        ElementDetails elementDetails = null
        List<ObjectWritable> cellWritableContents = cell.contents.collect { DObject object ->
            ObjectWritableFactory<? extends DObject> writerFactory = writerFactoryFactory.createWriter(object)
            ObjectWritable cellChildWritable = writerFactory.createFor(cellWorkContext, object, elementDetails)
            elementDetails = cellChildWritable.elementDetails
            cellWorkContext.contentsBounds = cellWorkContext.contentsBounds.offset((float) (elementDetails.containingBounds.bottom - cellWorkContext.contentsBounds.top), 0, 0, 0)
            cellChildWritable
        }

        float maxHeight = cellWritableContents.elementDetails.inject(0f) { float accum, ElementDetails details -> (float) (accum + details.containingBounds.height) }
        float lastLineDescent = 0
        // adjust if cell has border and tallest cell's last writable is for a borderless paragraph
        float height = 1 + (cell.borderBottom ? maxHeight - descentMultiplier.apply(lastLineDescent) : maxHeight)

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