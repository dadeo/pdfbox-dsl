package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleWriterTest extends Specification {
    private HorizontalRuleContextFactory contextFactory = Mock(HorizontalRuleContextFactory)
    private CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster = Mock(CurrentLocationAdjuster)
    private HorizontalRuleBoundsCalculator boundsCalculator = Mock(HorizontalRuleBoundsCalculator)
    private HorizontalRuleContentsDrawer contentsDrawer = Mock(HorizontalRuleContentsDrawer)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private HorizontalRuleElementDetailsFactory elementDetailsFactory = Mock(HorizontalRuleElementDetailsFactory)
    private HorizontalRuleWriter horizontalRuleWriter = new HorizontalRuleWriter(contextFactory: contextFactory,
                                                                                 currentLocationAdjuster: currentLocationAdjuster,
                                                                                 boundsCalculator: boundsCalculator,
                                                                                 contentsDrawer: contentsDrawer,
                                                                                 borderDrawer: borderDrawer,
                                                                                 elementDetailsFactory: elementDetailsFactory)
    private DContext parentContext = Mock(DContext)
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private ElementDetails previousElementDetails = Mock(ElementDetails)

    def "write"() {
        given:
        DContext horizontalRuleContext = Mock(DContext)

        when:
        horizontalRuleWriter.write(parentContext, horizontalRule, previousElementDetails)

        then:
        1 * contextFactory.createContextFrom(parentContext, horizontalRule) >> horizontalRuleContext
        1 * currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        1 * boundsCalculator.addCalculationsTo(horizontalRuleContext, horizontalRule)
        1 * contentsDrawer.drawFor(horizontalRule, horizontalRuleContext)
        1 * borderDrawer.drawFor(horizontalRule, horizontalRuleContext)
        1 * elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
        0 * _
    }

}