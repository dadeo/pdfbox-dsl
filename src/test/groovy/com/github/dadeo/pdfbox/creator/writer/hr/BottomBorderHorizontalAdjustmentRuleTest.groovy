package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification
import spock.lang.Unroll

class BottomBorderHorizontalAdjustmentRuleTest extends Specification {
    private static final float NONE = 0
    private static final float ADJUSTED = -10
    private static final float MODIFIED = -20
    private DescentMultiplier descentMultiplier = Mock(DescentMultiplier)
    private BottomBorderHorizontalAdjustmentRule rule = new BottomBorderHorizontalAdjustmentRule(descentMultiplier: descentMultiplier)
    private DContext context = new DContext()
    private DHorizontalRule horizontalRule = Mock(DHorizontalRule)

    @Unroll
    def "adjust current location by previous element's last line descent when it has a bottom border and current element has a top, left, or right border"() {
        given:
        ParagraphElementDetails previousElementDetails = new ParagraphElementDetails(hasBottomBorder: previousBorder, lastLineDescent: -10)
        descentMultiplier.apply(ADJUSTED) >> MODIFIED

        expect:
        rule.calculateAdjustmentFor(context, horizontalRule, previousElementDetails) == expectedAdjustment

        where:
        previousBorder | expectedAdjustment
        false          | MODIFIED
        true           | NONE
    }

}