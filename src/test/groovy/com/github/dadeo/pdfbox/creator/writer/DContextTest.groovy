package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.Justification
import com.github.dadeo.pdfbox.model.VerticalAlignment
import spock.lang.Specification


class DContextTest extends Specification {

    def "textJustification defaults to LEFT"() {
        expect:
        new DContext().textJustification == Justification.LEFT
    }

    def "setTextJustifcation overrides previous value when not null"() {
        given:
        DContext context = new DContext()

        when:
        context.textJustification = newValue

        then:
        context.textJustification == expectedValue

        where:
        newValue             | expectedValue
        Justification.LEFT   | Justification.LEFT
        Justification.CENTER | Justification.CENTER
        Justification.RIGHT  | Justification.RIGHT
        null                 | Justification.LEFT
    }

    def "verticalAlignment defaults to TOP"() {
        expect:
        new DContext().verticalAlignment == VerticalAlignment.TOP
    }

    def "setVerticalAlignment overrides previous value when not null"() {
        given:
        DContext context = new DContext()

        when:
        context.verticalAlignment = newValue

        then:
        context.verticalAlignment == expectedValue

        where:
        newValue                 | expectedValue
        VerticalAlignment.TOP    | VerticalAlignment.TOP
        VerticalAlignment.MIDDLE | VerticalAlignment.MIDDLE
        VerticalAlignment.BOTTOM | VerticalAlignment.BOTTOM
        null                     | VerticalAlignment.TOP
    }

}