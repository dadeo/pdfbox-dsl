package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DHorizontalRule


class HorizontalRuleElementDetailsFactory {

    ElementDetails createFor(DHorizontalRule horizontalRule, DContext context) {
        new HorizontalRuleElementDetails(containingBounds: context.containingBounds)
    }

}