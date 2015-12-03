package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleBoundsCalculationsTest extends Specification {
    private HorizontalRuleBoundsCalculations calculations = new HorizontalRuleBoundsCalculations()
    private DContext horizontalRuleContext = new DContext()
    private DHorizontalRule horizontalRule = new DHorizontalRule()

    def "containing bounds when no margin, no border, and no padding"() {
        given:
        horizontalRuleContext.containingBounds = new DBounds(500, 600, 100, 72)
        horizontalRule.thickness = 150

        expect:
        calculations.calculateContainingBounds(horizontalRuleContext, horizontalRule) == new DBounds(500, 600, 350, 72)
    }

    def "containing bounds affected by margin"() {
        given:
        horizontalRule.marginTop = 5
        horizontalRule.marginRight = 10
        horizontalRule.marginBottom = 15
        horizontalRule.marginLeft = 20

        horizontalRuleContext.containingBounds = new DBounds(500, 600, 100, 72)
        horizontalRule.thickness = 150

        expect:
        calculations.calculateContainingBounds(horizontalRuleContext, horizontalRule) == new DBounds(500, 600, 330, 72)
    }

    def "containing bounds affected by border"() {
        given:
        horizontalRule.borderTop = 5
        horizontalRule.borderRight = 10
        horizontalRule.borderBottom = 15
        horizontalRule.borderLeft = 20

        horizontalRuleContext.containingBounds = new DBounds(500, 600, 100, 72)
        horizontalRule.thickness = 150

        expect:
        calculations.calculateContainingBounds(horizontalRuleContext, horizontalRule) == new DBounds(500, 600, 330, 72)
    }

    def "containing bounds affected by padding"() {
        given:
        horizontalRule.paddingTop = 5
        horizontalRule.paddingRight = 10
        horizontalRule.paddingBottom = 15
        horizontalRule.paddingLeft = 20

        horizontalRuleContext.containingBounds = new DBounds(500, 600, 100, 72)
        horizontalRule.thickness = 150

        expect:
        calculations.calculateContainingBounds(horizontalRuleContext, horizontalRule) == new DBounds(500, 600, 330, 72)
    }

    def "containing bounds affected by margin, border, and padding"() {
        given:
        horizontalRule.margin = 20
        horizontalRule.border = 20
        horizontalRule.padding = 20

        horizontalRuleContext.containingBounds = new DBounds(500, 600, 100, 72)
        horizontalRule.thickness = 150

        expect:
        calculations.calculateContainingBounds(horizontalRuleContext, horizontalRule) == new DBounds(500, 600, 230, 72)
    }

    def "border bounds when no margin, no border, and no padding"() {
        given:
        horizontalRuleContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(horizontalRuleContext, horizontalRule) == new DBounds(800, 600, 100, 72)
    }

    def "border bounds affected by margin"() {
        given:
        horizontalRule.marginTop = 5
        horizontalRule.marginRight = 10
        horizontalRule.marginBottom = 15
        horizontalRule.marginLeft = 20

        horizontalRuleContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(horizontalRuleContext, horizontalRule) == new DBounds(795, 590, 115, 92)
    }

    def "border bounds not affected by border or padding"() {
        given:
        horizontalRule.border = 20
        horizontalRule.padding = 20

        horizontalRuleContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(horizontalRuleContext, horizontalRule) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds when no margin, no border, and no padding"() {
        given:
        horizontalRuleContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(horizontalRuleContext, horizontalRule) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds not affected by margin (border bounds already includes margin offsets)"() {
        given:
        horizontalRule.margin = 20

        horizontalRuleContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(horizontalRuleContext, horizontalRule) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds affected by border"() {
        given:
        horizontalRule.borderTop = 5
        horizontalRule.borderRight = 10
        horizontalRule.borderBottom = 15
        horizontalRule.borderLeft = 20

        horizontalRuleContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(horizontalRuleContext, horizontalRule) == new DBounds(795, 590, 115, 92)
    }

    def "contents bounds affected by padding"() {
        given:
        horizontalRule.paddingTop = 5
        horizontalRule.paddingRight = 10
        horizontalRule.paddingBottom = 15
        horizontalRule.paddingLeft = 20

        horizontalRuleContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(horizontalRuleContext, horizontalRule) == new DBounds(795, 590, 115, 92)
    }

    def "contents bounds affected by border and padding"() {
        given:
        horizontalRule.margin = 20   // no affect
        horizontalRule.border = 20
        horizontalRule.padding = 20

        horizontalRuleContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(horizontalRuleContext, horizontalRule) == new DBounds(760, 560, 140, 112)
    }

}