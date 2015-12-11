package com.github.dadeo.pdfbox.creator.writer.util

import com.github.dadeo.pdfbox.model.VerticalAlignment
import spock.lang.Specification

class VerticalAlignmentCalculatorTest extends Specification {
    private VerticalAlignmentCalculator calculator = new VerticalAlignmentCalculator()

    def "calculateOffsetFor"() {
        expect:

        calculator.calculateOffsetFor(verticalAlignment, currentHeight, maxHeight) == expectedHeight

        where:

        verticalAlignment        | currentHeight | maxHeight | expectedHeight
        VerticalAlignment.TOP    | 100f          | 200f      | 0f
        VerticalAlignment.MIDDLE | 100f          | 200f      | 50f
        VerticalAlignment.BOTTOM | 100f          | 200f      | 100f
    }

}