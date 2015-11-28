package com.github.dadeo.pdfbox.model.support

import spock.lang.Specification


class BorderFragmentLengthAndSpacingCalculatorTest extends Specification {
    private BorderFragmentLengthAndSpacingCalculator calculator = new BorderFragmentLengthAndSpacingCalculator()

    def "calculate border fragment and spacing length when both are set to zero"() {
        expect:
        calculator.calculate(90, 5, 0, 0) == new BorderFragmentLengthAndSpacing(10, 10)
    }

    def "calculate border fragment length when spacing is set"() {
        expect:
        calculator.calculate(135, 7, 0, 5) == new BorderFragmentLengthAndSpacing(15, 5)
    }

    def "calculate border fragment spacing length when fragment length is set"() {
        expect:
        calculator.calculate(90, 5, 8, 0) == new BorderFragmentLengthAndSpacing(8, 12.5f)
    }

    def "exception is thrown when both fragment length and spacing length are specified"() {
        when:
        calculator.calculate(90, 5, 8, 5) == new BorderFragmentLengthAndSpacing(10, 12.5f)

        then:
        RuntimeException e = thrown()
        e.message == "Either fragment length of spacing may be specified, not both."
    }

}