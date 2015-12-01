package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class BoundedTextBlockTokenWriterTest extends Specification {
    private TextBlockCurrentLocationRepositioner currentLocationRepositioner = Mock(TextBlockCurrentLocationRepositioner)
    private BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationRepositioner: currentLocationRepositioner)
    private StringToken token = new StringToken()
    private DWriter dWriter = Mock(DWriter)
    private DFont font = Mock(DFont)

    def "writes token and returns the location to place next token"() {
        given:
        DPoint currentLocation = new DPoint(72, 500)
        DPoint repositionedLocation = new DPoint(80, 500)
        token.font = font
        token.text = "yo dog"
        token.size = 8

        when:
        DPoint newLocation = tokenWriter.write(token, currentLocation, dWriter)

        then:
        newLocation == repositionedLocation
        1 * currentLocationRepositioner.repositionForNextToken(token, currentLocation) >> repositionedLocation
        1 * dWriter.writeText("yo dog", currentLocation, font)
    }

}