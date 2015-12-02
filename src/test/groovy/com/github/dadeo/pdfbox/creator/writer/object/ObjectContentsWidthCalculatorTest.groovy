package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.model.*
import spock.lang.Specification

class ObjectContentsWidthCalculatorTest extends Specification {
    private ObjectContentsWidthCalculator calculator = new ObjectContentsWidthCalculator()

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


    def "calculate when object is not Bordered or Padded"() {
        given:
        MarginedTestDObject object = new MarginedTestDObject()
        object.margin = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    def "calculate when object is not Margined or Padded"() {
        given:
        BorderedTestDObject object = new BorderedTestDObject() {}
        object.border = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    def "calculate when object is not Margined or Bordered"() {
        given:
        PaddedTestDObject object = new PaddedTestDObject() {}
        object.padding = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    private class MarginedTestDObject implements DObject, Margined {}

    private class BorderedTestDObject implements DObject, Bordered {}

    private class PaddedTestDObject implements DObject, Padded {}

}