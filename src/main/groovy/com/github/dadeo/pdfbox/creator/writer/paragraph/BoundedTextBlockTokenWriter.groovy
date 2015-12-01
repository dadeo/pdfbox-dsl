package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DPoint


class BoundedTextBlockTokenWriter {
    TextBlockCurrentLocationPositioner currentLocationPositioner

    DPoint write(StringToken token, DPoint currentLocation, DWriter dWriter) {
        DPoint adjustedLocation = currentLocationPositioner.repositionForCurrentToken(token, currentLocation)
        dWriter.writeText(token.text, adjustedLocation, token.font)
        currentLocationPositioner.repositionForNextToken(token, adjustedLocation)
    }

}