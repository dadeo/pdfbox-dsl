package com.github.dadeo.pdfbox.creator.writer.text

import com.github.dadeo.pdfbox.model.DFont
import spock.lang.Specification

class AssignedLineTest extends Specification {

    def "height is the maximum height of the font"() {
        expect:
        List<StringToken> tokens = fontSizes.collect { new StringToken(font: new DFont(size: it)) }
        new AssignedLine(tokens: tokens).height == height

        where:
        fontSizes    | height
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 7
        [5, 7, 3, 8] | 8
        [8, 7, 3, 5] | 8
    }

    def "width is the sum of all token widths added together"() {
        expect:
        List<StringToken> tokens = tokenWidths.collect { new StringToken(size: it) }
        new AssignedLine(tokens: tokens).width == width

        where:
        tokenWidths  | width
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 15
        [5, 7, 3, 8] | 23
    }

    def "descent is the maximum descent of the font"() {
        given:
        List<StringToken> tokens = fontDescents.collect {
            StringToken token = Mock(StringToken)
            1 * token.descent >> it
            token
        }

        expect:
        new AssignedLine(tokens: tokens).descent == descent

        where:
        fontDescents     | descent
        []               | 0
        [-5]             | -5
        [-5, -7, -3]     | -7
        [-5, -7, -3, -8] | -8
        [-8, -7, -3, -5] | -8
    }

}