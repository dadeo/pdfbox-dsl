package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleWritableFactoryTest extends Specification {
    private ObjectContextFactory contextFactory = Mock(ObjectContextFactory)
    private CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster = Mock(CurrentLocationAdjuster)
    private HorizontalRuleBoundsCalculator boundsCalculator = Mock(HorizontalRuleBoundsCalculator)
    private HorizontalRuleElementDetailsFactory elementDetailsFactory = Mock(HorizontalRuleElementDetailsFactory)
    private HorizontalRuleWritableFactory horizontalRuleWritableFactory = new HorizontalRuleWritableFactory(contextFactory: contextFactory,
                                                                                                            currentLocationAdjuster: currentLocationAdjuster,
                                                                                                            boundsCalculator: boundsCalculator,
                                                                                                            elementDetailsFactory: elementDetailsFactory)
    private DContext parentContext = Mock(DContext)
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private ElementDetails previousElementDetails = Mock(ElementDetails)
    private ElementDetails currentElementDetails = Mock(ElementDetails)

    def "createFor returns a configured writable"() {
        given:
        DContext horizontalRuleContext = Mock(DContext)

        when:
        HorizontalRuleWritable elementDetails = horizontalRuleWritableFactory.createFor(parentContext, horizontalRule, previousElementDetails) as HorizontalRuleWritable

        then:
        elementDetails.horizontalRule.is horizontalRule
        elementDetails.context.is horizontalRuleContext
        elementDetails.elementDetails.is currentElementDetails

        1 * contextFactory.createContextFrom(parentContext) >> horizontalRuleContext
        1 * currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        1 * boundsCalculator.addCalculationsTo(horizontalRuleContext, horizontalRule)
        1 * elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext) >> currentElementDetails
        0 * _
    }

}