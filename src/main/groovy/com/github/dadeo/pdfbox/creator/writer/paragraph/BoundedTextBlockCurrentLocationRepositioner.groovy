package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


class BoundedTextBlockCurrentLocationRepositioner {

    DPoint repositionForLine(DPoint currentLocation, DBounds contentsBounds, AssignedLine line) {
        new DPoint(x: contentsBounds.left, y: currentLocation.y - line.height)
    }

}