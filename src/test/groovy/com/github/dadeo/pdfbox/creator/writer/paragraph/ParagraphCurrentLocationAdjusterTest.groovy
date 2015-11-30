package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification
import spock.lang.Unroll

class ParagraphCurrentLocationAdjusterTest extends Specification {
    private final float ORIGINAL_Y = 500
    private ParagraphCurrentLocationAdjuster adjuster = new ParagraphCurrentLocationAdjuster()
    private DContext context = new DContext(currentLocation: new DPoint(72, ORIGINAL_Y))
    private Bordered bordered = Mock(Bordered)
    private ParagraphElementDetails previousElementDetails = new ParagraphElementDetails(0, false, null)

    @Unroll
    def "adjusts current location by sum of rule calculated offsets"() {
        given:
        adjuster.adjustmentRules = ruleOffsets.collect {
            ParagraphAdjustmentRule rule = Mock(ParagraphAdjustmentRule)
            rule.calculateAdjustmentFor(context, bordered, previousElementDetails) >> it
            rule
        }

        adjuster.adjustFor(context, bordered, previousElementDetails)

        expect:
        context.currentLocation == new DPoint(72f, (float) (ORIGINAL_Y + expectedY))

        where:
        ruleOffsets   | expectedY
        []            | 0
        [-5]          | -5
        [0, -5, 2, 7] | 4
    }

}