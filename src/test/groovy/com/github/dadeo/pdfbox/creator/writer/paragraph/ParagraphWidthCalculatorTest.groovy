package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification


class ParagraphWidthCalculatorTest extends Specification {
    private ParagraphWidthCalculator calculator = new ParagraphWidthCalculator()

    def "calculate when no margins, border, and spacing"() {
        expect:
        calculator.calculateFor(new DParagraph(), new DBounds(0, 600, 0, 100)) == 500f
    }

    def "calculate when only margin"() {
        expect:
        calculator.calculateFor(new DParagraph(marginLeft: 10, marginRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when only border"() {
        expect:
        calculator.calculateFor(new DParagraph(borderLeft: 10, borderRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when only padding"() {
        expect:
        calculator.calculateFor(new DParagraph(paddingLeft: 10, paddingRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when margin, border, and padding"() {
        given:

        DParagraph paragraph = new DParagraph(marginLeft: 5,
                                              marginRight: 10,
                                              borderLeft: 10,
                                              borderRight: 20,
                                              paddingLeft: 50,
                                              paddingRight: 100)

        expect:

        calculator.calculateFor(paragraph, new DBounds(0, 600, 0, 100)) == 305f
    }

}