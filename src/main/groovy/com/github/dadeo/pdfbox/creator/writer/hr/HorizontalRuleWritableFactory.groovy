package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritableFactory implements ObjectWritableFactory<DHorizontalRule> {
    ObjectContextFactory contextFactory
    CurrentLocationAdjuster<DHorizontalRule> currentLocationAdjuster
    ObjectBoundsCalculator objectBoundsCalculator
    HorizontalRuleElementDetailsFactory elementDetailsFactory

    @Override
    ObjectWritable createFor(DContext pageContext, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        DContext horizontalRuleContext = contextFactory.createContextFrom(pageContext, horizontalRule)
        currentLocationAdjuster.adjustFor(horizontalRuleContext, horizontalRule, previousElementDetails)
        objectBoundsCalculator.resizeBoundsToHeight(horizontalRule.thickness, horizontalRuleContext)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
        new HorizontalRuleWritable(horizontalRule, horizontalRuleContext, currentElementDetails)
    }
}