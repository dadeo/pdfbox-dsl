package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


interface TextBlockCurrentLocationPositioner {

    DPoint repositionForLine(DPoint currentLocation, DBounds contentsBounds, AssignedLine line)

    DPoint repositionForNextToken(StringToken token, DPoint currentLocation)

}