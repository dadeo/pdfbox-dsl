package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint

class BoundedTextBlockWriter {
    BoundedTextBlockLineWriter lineWriter = new BoundedTextBlockLineWriter()

    void write(BoundedTextBlock textBlock, DContext context) {
        DWriter writer = context.writer
        DBounds contentsBounds = context.contentsBounds
        DPoint currentLocation = context.contentsBounds.leftTop()

        textBlock.assignedLines.inject(currentLocation) { DPoint location, AssignedLine line ->
            lineWriter.write(line, contentsBounds, location, writer)
        }
    }

}