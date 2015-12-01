package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritable implements ObjectWritable {
    private HorizontalRuleContentsDrawer contentsDrawer = BootStrap.horizontalRuleContentsDrawer
    private BorderDrawer borderDrawer = BootStrap.borderDrawer
    private DHorizontalRule horizontalRule
    private DContext context
    private ElementDetails elementDetails

    HorizontalRuleWritable(DHorizontalRule horizontalRule, DContext context, ElementDetails elementDetails) {
        this.horizontalRule = horizontalRule
        this.context = context
        this.elementDetails = elementDetails
    }

    @Override
    void write() {
        contentsDrawer.drawFor(horizontalRule, context)
        borderDrawer.drawFor(horizontalRule, context)
    }

    @Override
    ElementDetails getElementDetails() {
        elementDetails
    }
}