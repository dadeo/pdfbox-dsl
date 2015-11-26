package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class ParagraphBoundsCalculatorTest extends Specification {
    private static final DBounds CONTAINING_BOUNDS = new DBounds()
    private static final DBounds BORDER_BOUNDS = new DBounds()
    private static final DBounds CONTENTS_BOUNDS = new DBounds()
    private ParagraphBoundsCalculations calculations = Mock(ParagraphBoundsCalculations)
    private ParagraphBoundsCalculator calculator = new ParagraphBoundsCalculator(calculations: calculations)

    def "AddCalculationsTo"() {
        given:
        DContext paragraphContext = new DContext()
        DParagraph paragraph = new DParagraph()
        BoundedTextBlock textBlock = new BoundedTextBlock()

        when:
        calculator.addCalculationsTo(paragraphContext, paragraph, textBlock)

        then:
        1 * calculations.calculateContainingBounds(sameInstance(paragraphContext), sameInstance(paragraph), sameInstance(textBlock)) >> CONTAINING_BOUNDS
        1 * calculations.calculateBorderBounds(sameInstance(paragraphContext), sameInstance(paragraph)) >> BORDER_BOUNDS
        1 * calculations.calculateContentsBounds(sameInstance(paragraphContext), sameInstance(paragraph)) >> CONTENTS_BOUNDS

        paragraphContext.containingBounds.is CONTAINING_BOUNDS
        paragraphContext.borderBounds.is BORDER_BOUNDS
        paragraphContext.contentsBounds.is CONTENTS_BOUNDS
    }

}