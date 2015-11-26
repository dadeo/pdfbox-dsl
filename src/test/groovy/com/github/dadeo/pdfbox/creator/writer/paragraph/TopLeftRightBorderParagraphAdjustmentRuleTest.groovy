package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification
import spock.lang.Unroll


class TopLeftRightBorderParagraphAdjustmentRuleTest extends Specification {
    private static final float NONE = 0
    private static final float ADJUSTED = -10
    private TopLeftRightBorderParagraphAdjustmentRule rule = new TopLeftRightBorderParagraphAdjustmentRule()
    private DContext context = new DContext(currentLocation: new DPoint(72, NONE))
    private Bordered bordered = Mock(Bordered)

    @Unroll
    def "adjust current location by previous element's last line descent when it has a bottom border and current element has a top, left, or right border"() {
        given:
        ParagraphPreviousElementDetails previousElementDetails = new ParagraphPreviousElementDetails(hasBottomBorder: previousBorder, lastLineDescent: -10)
        bordered.borderTop >> borderTop
        bordered.borderRight >> borderRight
        bordered.borderBottom >> borderBottom
        bordered.borderLeft >> borderLeft

        expect:
        rule.calculateAdjustmentFor(context, bordered, previousElementDetails) == expectedAdjustment

        where:
        previousBorder | borderTop | borderRight | borderBottom | borderLeft | expectedAdjustment
        false          | 0         | 0           | 0            | 0          | NONE
        false          | 10        | 0           | 0            | 0          | ADJUSTED
        false          | 0         | 10          | 0            | 0          | ADJUSTED
        false          | 0         | 0           | 10           | 0          | NONE
        false          | 0         | 0           | 0            | 10         | ADJUSTED
        false          | 10        | 10          | 10           | 10         | ADJUSTED
        true           | 0         | 0           | 0            | 0          | NONE
        true           | 10        | 0           | 0            | 0          | NONE
        true           | 0         | 10          | 0            | 0          | NONE
        true           | 0         | 0           | 10           | 0          | NONE
        true           | 0         | 0           | 0            | 10         | NONE
        true           | 10        | 10          | 10           | 10         | NONE
    }

}