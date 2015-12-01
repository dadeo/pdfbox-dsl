package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class RightJustifiedTextBlockCurrentLocationPositionerTest extends Specification {
    private static final float RIGHT = 600
    private static final float CURRENT_Y = 500
    private static final float LINE_WIDTH = 150
    private static final float LINE_HEIGHT = 12
    private RightJustifiedTextBlockCurrentLocationPositioner repositioner = new RightJustifiedTextBlockCurrentLocationPositioner()
    private AssignedLine line = Mock(AssignedLine)

    def "repositions the line to start below the point with x calculated by subtracting the line width from the right side"() {
        given:
        line.height >> LINE_HEIGHT
        line.width >> LINE_WIDTH

        expect:
        repositioner.repositionForLine(new DPoint(225, CURRENT_Y), new DBounds(800, RIGHT, 100, 72), line) == new DPoint((float) (RIGHT - LINE_WIDTH), (float) (CURRENT_Y - LINE_HEIGHT))
    }

    def "repositioning for the current token returns the current location"() {
        given:
        DPoint lastLocation = new DPoint(225, 500)
        StringToken token = new StringToken(size: 15)

        expect:
        repositioner.repositionForCurrentToken(token, lastLocation) == lastLocation
    }

    def "repositioning for the next token increments the current lcoation by the width of the token"() {
        given:
        DPoint lastLocation = new DPoint(225, 500)
        StringToken token = new StringToken(size: 15)

        expect:
        repositioner.repositionForNextToken(token, lastLocation) == new DPoint(240, 500)
    }

}