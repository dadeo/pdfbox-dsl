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
        horizontalRuleContext.contentsBounds = new DBounds(600f, 700f, 500f, 100f)
        horizontalRule.thickness = 4f
        horizontalRule.color = Color.red

        when:
        contentsDrawer.drawFor(horizontalRule, horizontalRuleContext)

        then:
        1 * dWriter.drawLine(new DPoint(99.5f, 550f), new DPoint(700.5f, 550f), 4, Color.red)
    }

}