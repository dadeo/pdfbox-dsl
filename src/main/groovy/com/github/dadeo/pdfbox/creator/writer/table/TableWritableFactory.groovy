/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.*
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.creator.writer.util.VerticalAlignmentCalculator
import com.github.dadeo.pdfbox.model.Cell
import com.github.dadeo.pdfbox.model.PdfComponent
import com.github.dadeo.pdfbox.model.Table

class TableWritableFactory implements ObjectWritableFactory<Table> {
    ObjectContextFactory objectContextFactory
    ObjectWritableFactoryFactory writerFactoryFactory
    ObjectBoundsCalculator objectBoundsCalculator
    TableColumnWidthCalculator columnWidthCalculator
    DescentMultiplier descentMultiplier
    VerticalAlignmentCalculator verticalAlignmentCalculator

    @Override
    ObjectWritable createFor(DContext pageContext, Table table) {
        DContext tableContext = objectContextFactory.createContextFrom(pageContext, table)
        tableContext.verticalAlignment = table.verticalAlignment

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

        new TableWritable(table, rowWritables, tableContext)
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
        new RowWritable((List<CellWritable>) cellWritables.clone(), rowContext)
    }

    protected CellWritable createCellWritable(DContext columnContext, Cell cell, float cellWidth) {
        DContext cellContext = objectContextFactory.createContextFrom(columnContext, cell)
        cellContext.verticalAlignment = cell.verticalAlignment
        DContext cellWorkContext = cellContext.clone()

        List<ObjectWritable> cellWritableContents = cell.contents.collect { PdfComponent object ->
            ObjectWritableFactory<? extends PdfComponent> writerFactory = writerFactoryFactory.createWriter(object)
            ObjectWritable cellChildWritable = writerFactory.createFor(cellWorkContext, object)
            if (cellChildWritable.positionType == PositionType.RELATIVE)
                objectBoundsCalculator.shrinkBoundsVertically(cellChildWritable.context.containingBounds.height, cellWorkContext)
            cellChildWritable
        }

        float maxHeight = cellWritableContents ? cellWritableContents.context.containingBounds.height.sum() : 0

        objectBoundsCalculator.resizeBoundsToHeight(maxHeight, cellContext)

        new CellWritable(cell: cell, context: cellContext, contents: cellWritableContents)
    }

}