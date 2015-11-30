package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleContextFactoryTest extends Specification {

    private HorizontalRuleContextFactory factory = new HorizontalRuleContextFactory()
    private DContext clonedContext = new DContext()
    private DContext pageContext = Mock(DContext) {
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        expect:

        factory.createContextFrom(pageContext, new DHorizontalRule()).is clonedContext
    }

    def "horizontal rule context contains page context as parent"() {
        expect:

        factory.createContextFrom(pageContext, new DHorizontalRule()).parent.is pageContext
    }

}