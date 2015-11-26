package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification

class BoundedTextBlockLineWriterTest extends Specification {
    private BoundedTextBlockCurrentLocationRepositioner currentLocationPositioner = Mock(BoundedTextBlockCurrentLocationRepositioner)
    private BoundedTextBlockTokenWriter tokenWriter = Mock(BoundedTextBlockTokenWriter)
    private BoundedTextBlockLineWriter lineWriter = new BoundedTextBlockLineWriter(tokenWriter: tokenWriter,
                                                                                   currentLocationPositioner: currentLocationPositioner)
    private StringToken token1 = new StringToken()
    private StringToken token2 = new StringToken()
    private StringToken token3 = new StringToken()
    private AssignedLine line = new AssignedLine()
    private DBounds contentsBounds = GroovyMock(DBounds)
    private DPoint currentLocation = new DPoint(100, 100)
    private DPoint location1 = new DPoint(100, 800)
    private DPoint location2 = new DPoint(100, 700)
    private DPoint location3 = new DPoint(100, 600)
    private DPoint location4 = new DPoint(100, 500)
    private DWriter dWriter = Mock(DWriter)

    def "passes the repositioned location in to the first call of token writer"() {
        given:
        line.tokens = [token1]

        when:
        lineWriter.write(line, contentsBounds, currentLocation, dWriter)

        then:
        1 * currentLocationPositioner.repositionForLine(currentLocation, contentsBounds, line) >> location1
        1 * tokenWriter.write(token1, location1, dWriter) >> location2
    }

    def "passes location from previous invocation of tokenWriter to the next"() {
        given:
        line.tokens = [token1, token2, token3]

        when:
        lineWriter.write(line, contentsBounds, currentLocation, dWriter)

        then:
        1 * currentLocationPositioner.repositionForLine(currentLocation, contentsBounds, line) >> location1
        1 * tokenWriter.write(token1, location1, dWriter) >> location2
        1 * tokenWriter.write(token2, location2, dWriter) >> location3
        1 * tokenWriter.write(token1, location3, dWriter) >> location4
    }

    def "returns the last location"() {
        given:
        line.tokens = [token1, token2, token3]

        when:
        DPoint result = lineWriter.write(line, contentsBounds, currentLocation, dWriter)

        then:
        1 * tokenWriter.write(*_) >> location2
        1 * tokenWriter.write(*_) >> location3
        1 * tokenWriter.write(*_) >> location4

        result == location4
    }

}