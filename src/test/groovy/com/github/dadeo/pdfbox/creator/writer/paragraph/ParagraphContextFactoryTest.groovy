package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphContextFactoryTest extends Specification {
    private static final DFont PAGE_FONT = new DFont()
    private static final DFont PARAGRAPH_FONT = new DFont()

    private ParagraphContextFactory factory = new ParagraphContextFactory()
    private DContext clonedContext = new DContext(font: PAGE_FONT)
    private DContext pageContext = Mock(DContext) {
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        expect:

        factory.createContextFrom(pageContext, new DParagraph()).is clonedContext
    }

    def "DParagraph font overrides page context font"() {
        expect:

        factory.createContextFrom(pageContext, new DParagraph(font: PARAGRAPH_FONT)).font.is PARAGRAPH_FONT
    }

    def "paragraph context contains page context font when DParagraph font is null"() {
        expect:

        factory.createContextFrom(pageContext, new DParagraph()).font.is PAGE_FONT
    }

    def "paragraph context contains page context as parent"() {
        expect:

        factory.createContextFrom(pageContext, new DParagraph()).parent.is pageContext
    }
}