package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification


class PageContextFactoryTest extends Specification {
    private static final DFont DOCUMENT_FONT = new DFont()
    private static final DFont PAGE_FONT = new DFont()

    private PageContextFactory factory = new PageContextFactory()
    private DContext clonedContext = new DContext(font: DOCUMENT_FONT)
    private DContext documentContext = Mock(DContext) {
        1 * clone() >> clonedContext
    }

    def "document context is cloned and returned"() {
        expect:

        factory.createPageContextFrom(documentContext, new DPage()).is clonedContext
    }

    def "DPage font overrides document context font"() {
        expect:

        factory.createPageContextFrom(documentContext, new DPage(font: PAGE_FONT)).font.is PAGE_FONT
    }

    def "page context contains document context font when DPage font is null"() {
        expect:

        factory.createPageContextFrom(documentContext, new DPage()).font.is DOCUMENT_FONT
    }
}