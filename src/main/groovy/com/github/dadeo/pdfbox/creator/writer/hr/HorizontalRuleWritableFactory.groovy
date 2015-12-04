package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritableFactory implements ObjectWritableFactory<DHorizontalRule> {
    ObjectContextFactory contextFactory = new ObjectContextFactory()
    CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster = BootStrap.horizontalRuleCurrentLocationAdjuster
    ObjectBoundsCalculator objectBoundsCalculator = new ObjectBoundsCalculator()
    HorizontalRuleElementDetailsFactory elementDetailsFactory = new HorizontalRuleElementDetailsFactory()

    @Override
    ObjectWritable createFor(DContext pageContext, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        DContext horizontalRuleContext = contextFactory.createContextFrom(pageContext, horizontalRule)
        currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        objectBoundsCalculator.calculateActualBounds(horizontalRuleContext, horizontalRule.thickness)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
        new HorizontalRuleWritable(horizontalRule, horizontalRuleContext, currentElementDetails)
    }
}