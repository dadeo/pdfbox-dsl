package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification

import java.awt.*

class HorizontalRuleContentsDrawerTest extends Specification {
    private HorizontalRuleContentsDrawer contentsDrawer = new HorizontalRuleContentsDrawer()
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private DWriter dWriter = Mock(DWriter)
    private DContext horizontalRuleContext = new DContext(writer: dWriter)

    def "draws line centered between contents bounds"() {
        given:
        horizontalRuleContext.contentsBounds = new DBounds(600, 700, 500, 100)
        horizontalRule.thickness = 4
        horizontalRule.color = Color.red

        when:
        contentsDrawer.drawFor(horizontalRule, horizontalRuleContext)

        then:
        1 * dWriter.drawLine(new DPoint(100, 550), new DPoint(700, 550), 4, Color.red)
    }

}