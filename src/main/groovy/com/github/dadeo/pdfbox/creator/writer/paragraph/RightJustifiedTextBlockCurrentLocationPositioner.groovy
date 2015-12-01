package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


class RightJustifiedTextBlockCurrentLocationPositioner implements TextBlockCurrentLocationPositioner {

    DPoint repositionForLine(DPoint currentLocation, DBounds contentsBounds, AssignedLine line) {
        new DPoint(x: contentsBounds.right - line.width, y: currentLocation.y - line.height)
    }

    DPoint repositionForNextToken(StringToken token, DPoint currentLocation) {
        new DPoint(x: currentLocation.x + token.size, y: currentLocation.y)
    }

}