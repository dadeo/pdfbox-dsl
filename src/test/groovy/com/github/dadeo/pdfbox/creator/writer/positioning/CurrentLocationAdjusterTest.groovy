package com.github.dadeo.pdfbox.creator.writer.positioning

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification
import spock.lang.Unroll

class CurrentLocationAdjusterTest extends Specification {
    private final float ORIGINAL_Y = 500
    private final DBounds ORIGINAL_CONTAINING_BOUNDS = new DBounds(900, 600, 100, 100)
    private final DBounds ORIGINAL_BORDER_BOUNDS = new DBounds(850, 550, 150, 150)
    private final DBounds ORIGINAL_CONTENTS_BOUNDS = new DBounds(800, 500, 200, 200)
    private CurrentLocationAdjuster adjuster = new CurrentLocationAdjuster()
    private DContext context = new DContext()
    private Bordered bordered = Mock(Bordered)
    private ElementDetails previousElementDetails = Mock(ElementDetails)

    @Unroll
    def "adjusts current location by sum of rule calculated offsets"() {
        given:
        context.currentLocation = new DPoint(72, ORIGINAL_Y)
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds = ORIGINAL_BORDER_BOUNDS
        context.contentsBounds = ORIGINAL_CONTENTS_BOUNDS

        adjuster.adjustmentRules = ruleOffsets.collect {
            CurrentLocationAdjustmentRule rule = Mock(CurrentLocationAdjustmentRule)
            rule.calculateAdjustmentFor(context, bordered, previousElementDetails) >> it
            rule
        }

        when:
        adjuster.adjustFor(context, bordered, previousElementDetails)

        then:
        context.currentLocation == new DPoint(72f, (float) (ORIGINAL_Y + expectedY))
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS.offset(0, expectedY)
        context.borderBounds == ORIGINAL_BORDER_BOUNDS.offset(0, expectedY)
        context.contentsBounds == ORIGINAL_CONTENTS_BOUNDS.offset(0, expectedY)

        where:
        ruleOffsets   | expectedY
        []            | 0
        [-5]          | -5
        [0, -5, 2, 7] | 4
    }

}