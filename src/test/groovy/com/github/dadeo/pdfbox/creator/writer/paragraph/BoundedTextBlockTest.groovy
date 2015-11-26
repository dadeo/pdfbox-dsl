package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import spock.lang.Specification


class BoundedTextBlockTest extends Specification {
    private BoundedTextBlock boundedTextBlock = new BoundedTextBlock()

    def "height is calculated from the assigned lines"() {
        given:
        AssignedLine line1 = Mock(AssignedLine)
        AssignedLine line2 = Mock(AssignedLine)
        AssignedLine line3 = Mock(AssignedLine)
        boundedTextBlock.assignedLines = [line1, line2, line3]

        when:
        line1.height >> 10
        line2.height >> 5
        line3.height >> 12

        then:
        boundedTextBlock.height == 27
    }

    def "height is 0 when no assigned lines"() {
        given:
        boundedTextBlock.assignedLines = []

        expect:
        boundedTextBlock.height == 0
    }

    def "lastLineDescent is the descent of the last line or zero"() {
        given:
        boundedTextBlock.assignedLines = lineDescent.collect {
            AssignedLine assignedLine = Mock(AssignedLine)
            assignedLine.descent >> it
            assignedLine
        }

        expect:
        boundedTextBlock.lastLineDescent == lastLineDescent

        where:
        lineDescent  | lastLineDescent
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 3
        [5, 7, 3, 8] | 8
        [8, 7, 3, 5] | 5
    }

}