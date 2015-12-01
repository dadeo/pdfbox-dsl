package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification


class HorizontalRuleElementDetailsFactoryTest extends Specification {
    private HorizontalRuleElementDetailsFactory factory = new HorizontalRuleElementDetailsFactory()
    private DContext context = new DContext()
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private DBounds containingBounds = new DBounds(1, 2, 3, 4)

    def "creates details with containing bounds"() {
        given:
        context.containingBounds = containingBounds

        when:
        HorizontalRuleElementDetails details = factory.createFor(horizontalRule, context)

        then:
        details.containingBounds == containingBounds
    }

}