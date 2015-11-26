package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DPoint


class BoundedTextBlockTokenWriter {

    DPoint write(StringToken token, DPoint currentLocation, DWriter dWriter) {
        dWriter.writeText(token.text, currentLocation, token.font)
        new DPoint(x: currentLocation.x + token.size, y: currentLocation.y)
    }

}