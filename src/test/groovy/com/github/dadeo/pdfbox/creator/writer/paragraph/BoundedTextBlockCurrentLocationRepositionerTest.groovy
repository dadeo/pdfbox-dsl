package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class BoundedTextBlockCurrentLocationRepositionerTest extends Specification {
    BoundedTextBlockCurrentLocationRepositioner repositioner = new BoundedTextBlockCurrentLocationRepositioner()
    private AssignedLine line = Mock(AssignedLine)

    def "repositions the line to start below the point"() {
        given:
        line.height >> 12

        expect:
        repositioner.repositionForLine(new DPoint(225, 500), new DBounds(800, 600, 100, 72), line) == new DPoint(72, 488)
    }

}