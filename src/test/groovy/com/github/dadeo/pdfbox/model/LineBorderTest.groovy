package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter
import spock.lang.Specification

import java.awt.*

class LineBorderTest extends Specification {
    private LineBorder lineBorder = new LineBorder()
    private Bordered bordered = new Bordered() {}
    private DWriter writer = Mock(DWriter)

    def "bordered only has top border"() {
        given:
        bordered.borderTop = 6
        bordered.borderTopColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new DBounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new DPoint(99.5f, 97.5f), new DPoint(200.5f, 97.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has right border"() {
        given:
        bordered.borderRight = 6
        bordered.borderRightColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new DBounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new DPoint(197.5F, 100.5F), new DPoint(197.5F, -0.5F), 6, Color.red)
        0 * _
    }

    def "bordered only has bottom border"() {
        given:
        bordered.borderBottom = 6
        bordered.borderBottomColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new DBounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new DPoint(99.5f, 2.5f), new DPoint(200.5f, 2.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has left border"() {
        given:
        bordered.borderLeft = 6
        bordered.borderLeftColor = Color.red
        when:
        lineBorder.drawBorder(bordered, writer, new DBounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new DPoint(102.5f, 100.5f), new DPoint(102.5f, -0.5f), 6, Color.red)
        0 * _
    }

    def "bordered has all sides"() {
        given:
        bordered.border = 8

        when:
        lineBorder.drawBorder(bordered, writer, new DBounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new DPoint(99.5f, 96.5f), new DPoint(200.5f, 96.5f), 8, Color.black)    // top
        1 * writer.drawLine(new DPoint(196.5f, 100.5f), new DPoint(196.5f, -0.5f), 8, Color.black)  // right
        1 * writer.drawLine(new DPoint(99.5f, 3.5f), new DPoint(200.5f, 3.5f), 8, Color.black)      // bottom
        1 * writer.drawLine(new DPoint(103.5f, 100.5f), new DPoint(103.5f, -0.5f), 8, Color.black)  // left
        0 * _
    }

}