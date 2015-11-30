package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.page.PageObjectWriter
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWriter implements PageObjectWriter<DHorizontalRule> {
    HorizontalRuleContextFactory contextFactory = new HorizontalRuleContextFactory()
    CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster = BootStrap.horizontalRuleCurrentLocationAdjuster
    HorizontalRuleBoundsCalculator boundsCalculator = new HorizontalRuleBoundsCalculator()
    HorizontalRuleContentsDrawer contentsDrawer = new HorizontalRuleContentsDrawer()
    BorderDrawer borderDrawer = new BorderDrawer()
    HorizontalRuleElementDetailsFactory elementDetailsFactory = new HorizontalRuleElementDetailsFactory()

    @Override
    ElementDetails write(DContext pageContext, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        DContext horizontalRuleContext = contextFactory.createContextFrom(pageContext, horizontalRule)
        currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        boundsCalculator.addCalculationsTo(horizontalRuleContext, horizontalRule)
        contentsDrawer.drawFor(horizontalRule, horizontalRuleContext)
        borderDrawer.drawFor(horizontalRule, horizontalRuleContext)
        elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
    }

}