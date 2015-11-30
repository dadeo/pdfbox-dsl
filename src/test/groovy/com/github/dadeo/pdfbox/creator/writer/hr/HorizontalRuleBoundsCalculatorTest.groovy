package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class HorizontalRuleBoundsCalculatorTest extends Specification {
    private static final DBounds CONTAINING_BOUNDS = new DBounds()
    private static final DBounds BORDER_BOUNDS = new DBounds()
    private static final DBounds CONTENTS_BOUNDS = new DBounds()
    private HorizontalRuleBoundsCalculations calculations = Mock(HorizontalRuleBoundsCalculations)
    private HorizontalRuleBoundsCalculator calculator = new HorizontalRuleBoundsCalculator(calculations: calculations)

    def "add calculations to context for containing bounds, border bounds, and contents bounds"() {
        given:
        DContext context = new DContext()
        DHorizontalRule horizontalRule = new DHorizontalRule()

        when:
        calculator.addCalculationsTo(context, horizontalRule)

        then:
        1 * calculations.calculateContainingBounds(sameInstance(context), sameInstance(horizontalRule)) >> CONTAINING_BOUNDS
        1 * calculations.calculateBorderBounds(sameInstance(context), sameInstance(horizontalRule)) >> BORDER_BOUNDS
        1 * calculations.calculateContentsBounds(sameInstance(context), sameInstance(horizontalRule)) >> CONTENTS_BOUNDS

        context.containingBounds.is CONTAINING_BOUNDS
        context.borderBounds.is BORDER_BOUNDS
        context.contentsBounds.is CONTENTS_BOUNDS
    }

}