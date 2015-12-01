package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


class BoundedTextBlockLineWriter {
    TextBlockCurrentLocationPositioner currentLocationPositioner
    BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter()

    DPoint write(AssignedLine line, DBounds contentsBounds, DPoint currentLocation, DWriter writer) {
        DPoint repositionedLocation = currentLocationPositioner.repositionForLine(currentLocation, contentsBounds, line)

        line.tokens.inject(repositionedLocation) { DPoint location, StringToken token ->
            tokenWriter.write(token, location, writer)
        }
    }

}