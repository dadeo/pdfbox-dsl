package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DHorizontalRule


class HorizontalRuleElementDetailsFactory {

    HorizontalRulerElementDetails createFor(DHorizontalRule horizontalRule, DContext context) {
        new HorizontalRulerElementDetails(containingBounds: context.containingBounds)
    }

}