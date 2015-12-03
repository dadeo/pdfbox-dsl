package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritableFactory implements ObjectWritableFactory<DHorizontalRule> {
    ObjectContextFactory contextFactory = new ObjectContextFactory()
    CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster = BootStrap.horizontalRuleCurrentLocationAdjuster
    HorizontalRuleBoundsCalculator boundsCalculator = new HorizontalRuleBoundsCalculator()
    HorizontalRuleElementDetailsFactory elementDetailsFactory = new HorizontalRuleElementDetailsFactory()

    @Override
    ObjectWritable createFor(DContext pageContext, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        DContext horizontalRuleContext = contextFactory.createContextFrom(pageContext)
        currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        boundsCalculator.addCalculationsTo(horizontalRuleContext, horizontalRule)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
        new HorizontalRuleWritable(horizontalRule, horizontalRuleContext, currentElementDetails)
    }
}