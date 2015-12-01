package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class BoundedTextBlockTokenWriterTest extends Specification {
    private TextBlockCurrentLocationPositioner currentLocationRepositioner = Mock(TextBlockCurrentLocationPositioner)
    private BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
    private StringToken token = new StringToken()
    private DWriter dWriter = Mock(DWriter)
    private DFont font = Mock(DFont)

    def "writes token and returns the location to place next token"() {
        given:
        DPoint currentLocation = new DPoint(0, 1)
        DPoint preWriteRepositionedLocation = new DPoint(0, 2)
        DPoint postWriteRepositionedLocation = new DPoint(0, 3)

        token.font = font
        token.text = "yo dog"
        token.size = 8

        when:
        DPoint newLocation = tokenWriter.write(token, currentLocation, dWriter)

        then:
        newLocation == postWriteRepositionedLocation
        1 * currentLocationRepositioner.repositionForCurrentToken(token, currentLocation) >> preWriteRepositionedLocation
        1 * currentLocationRepositioner.repositionForNextToken(token, preWriteRepositionedLocation) >> postWriteRepositionedLocation
        1 * dWriter.writeText("yo dog", preWriteRepositionedLocation, font)
    }

}