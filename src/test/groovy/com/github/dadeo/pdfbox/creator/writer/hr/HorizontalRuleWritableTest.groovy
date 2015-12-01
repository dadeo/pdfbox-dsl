package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleWritableTest extends Specification {
    private HorizontalRuleContentsDrawer contentsDrawer = Mock(HorizontalRuleContentsDrawer)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private DContext context = new DContext()
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private ElementDetails elementDetails = Mock(ElementDetails)

    def "write paragraph orchestration"() {
        given:
        HorizontalRuleWritable paragraphWritable = new HorizontalRuleWritable(horizontalRule, context, elementDetails)
        paragraphWritable.contentsDrawer = contentsDrawer
        paragraphWritable.borderDrawer = borderDrawer

        when:
        paragraphWritable.write()

        then:
        1 * contentsDrawer.drawFor(horizontalRule, context)
        1 * borderDrawer.drawFor(horizontalRule, context)
    }

}