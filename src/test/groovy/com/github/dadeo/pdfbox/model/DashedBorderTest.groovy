package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacing
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacingCalculator
import spock.lang.Specification

import java.awt.*

class DashedBorderTest extends Specification {
    private BorderFragmentLengthAndSpacingCalculator calculator = Mock(BorderFragmentLengthAndSpacingCalculator)
    private DashedBorder dashedBorder = new DashedBorder(calculator: calculator)
    private Bordered bordered = new Bordered() {}
    private DWriter writer = Mock(DWriter)

    def "bordered only has top border"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        bordered.borderTop = 6
        bordered.borderTopColor = Color.red
        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(400, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(200, 797), new DPoint(220, 797), 6, Color.red)
        1 * writer.drawLine(new DPoint(250, 797), new DPoint(270, 797), 6, Color.red)
        1 * writer.drawLine(new DPoint(300, 797), new DPoint(320, 797), 6, Color.red)
        0 * _
    }

    def "bordered only has right border"() {
        given:
        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.borderRight = 6
        bordered.borderRightColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(700, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(597, 800), new DPoint(597, 780), 6, Color.red)
        1 * writer.drawLine(new DPoint(597, 750), new DPoint(597, 730), 6, Color.red)
        1 * writer.drawLine(new DPoint(597, 700), new DPoint(597, 680), 6, Color.red)
        0 * _
    }

    def "bordered only has bottom border"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        bordered.borderBottom = 6
        bordered.borderBottomColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(400, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(200, 103), new DPoint(220, 103), 6, Color.red)
        1 * writer.drawLine(new DPoint(250, 103), new DPoint(270, 103), 6, Color.red)
        1 * writer.drawLine(new DPoint(300, 103), new DPoint(320, 103), 6, Color.red)
        0 * _
    }

    def "bordered only has left border"() {
        given:
        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.borderLeft = 6
        bordered.borderLeftColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(700, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(203, 800), new DPoint(203, 780), 6, Color.red)
        1 * writer.drawLine(new DPoint(203, 750), new DPoint(203, 730), 6, Color.red)
        1 * writer.drawLine(new DPoint(203, 700), new DPoint(203, 680), 6, Color.red)
        0 * _
    }

    def "bordered has all sides"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.border = 8

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(400, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(200, 796), new DPoint(220, 796), 8, Color.black)
        1 * writer.drawLine(new DPoint(250, 796), new DPoint(270, 796), 8, Color.black)
        1 * writer.drawLine(new DPoint(300, 796), new DPoint(320, 796), 8, Color.black)

        1 * calculator.calculate(700, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(596, 800), new DPoint(596, 780), 8, Color.black)
        1 * writer.drawLine(new DPoint(596, 750), new DPoint(596, 730), 8, Color.black)
        1 * writer.drawLine(new DPoint(596, 700), new DPoint(596, 680), 8, Color.black)

        1 * calculator.calculate(400, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(200, 104), new DPoint(220, 104), 8, Color.black)
        1 * writer.drawLine(new DPoint(250, 104), new DPoint(270, 104), 8, Color.black)
        1 * writer.drawLine(new DPoint(300, 104), new DPoint(320, 104), 8, Color.black)

        1 * calculator.calculate(700, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(204, 800), new DPoint(204, 780), 8, Color.black)
        1 * writer.drawLine(new DPoint(204, 750), new DPoint(204, 730), 8, Color.black)
        1 * writer.drawLine(new DPoint(204, 700), new DPoint(204, 680), 8, Color.black)

        0 * _
    }

}