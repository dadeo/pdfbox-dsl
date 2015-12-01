package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


class CenterJustifiedTextBlockCurrentLocationPositioner implements TextBlockCurrentLocationPositioner {

    DPoint repositionForLine(DPoint currentLocation, DBounds contentsBounds, AssignedLine line) {
        float middleOfBounds = (contentsBounds.right - contentsBounds.left) / 2
        float halfOfLineWidth = line.width / 2
        new DPoint(x: contentsBounds.left + middleOfBounds - halfOfLineWidth, y: currentLocation.y - line.height)
    }

    DPoint repositionForNextToken(StringToken token, DPoint currentLocation) {
        new DPoint(x: currentLocation.x + token.size, y: currentLocation.y)
    }

}