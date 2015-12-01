package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DPoint


class BoundedTextBlockTokenWriter {
    TextBlockCurrentLocationRepositioner currentLocationRepositioner = new LeftJustifiedTextBlockCurrentLocationRepositioner()

    DPoint write(StringToken token, DPoint currentLocation, DWriter dWriter) {
        dWriter.writeText(token.text, currentLocation, token.font)
        currentLocationRepositioner.repositionForNextToken(token, currentLocation)
    }

}