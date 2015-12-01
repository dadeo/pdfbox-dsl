package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.DPoint


class HorizontalRuleContentsDrawer {

    void drawFor(DHorizontalRule horizontalRule, DContext horizontalRuleContext) {
        def contentBounds = horizontalRuleContext.contentsBounds

        def y = (float) (contentBounds.top - (contentBounds.top - contentBounds.bottom) / 2)
        def startPoint = new DPoint(contentBounds.left, y)
        def endPoint = new DPoint(contentBounds.right, y)

        horizontalRuleContext.writer.drawLine(startPoint, endPoint, horizontalRule.thickness, horizontalRule.color)
    }

}