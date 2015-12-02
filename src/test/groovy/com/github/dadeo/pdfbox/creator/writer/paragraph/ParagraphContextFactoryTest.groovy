package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DParagraph
import com.github.dadeo.pdfbox.model.Justification
import spock.lang.Specification

class ParagraphContextFactoryTest extends Specification {
    private static final DFont PAGE_FONT = new DFont()
    private static final DFont PARAGRAPH_FONT = new DFont()

    private ParagraphContextFactory factory = new ParagraphContextFactory()
    private DContext clonedContext = new DContext(font: PAGE_FONT)
    private DContext parentContext = Mock(DContext) {
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        expect:

        factory.createContextFrom(parentContext, new DParagraph()).is clonedContext
    }

    def "DParagraph font overrides page context font"() {
        expect:

        factory.createContextFrom(parentContext, new DParagraph(font: PARAGRAPH_FONT)).font.is PARAGRAPH_FONT
    }

    def "paragraph context contains parent context font when DParagraph font is null"() {
        expect:

        factory.createContextFrom(parentContext, new DParagraph()).font.is PAGE_FONT
    }

    def "paragraph context contains justification when one is specified"() {
        given:
        DParagraph paragraph = new DParagraph(justification: justification)

        when:
        DContext paragraphContext = factory.createContextFrom(parentContext, paragraph)

        then:
        paragraphContext.textJustification == justification

        where:
        justification << Justification.values()
    }

    def "paragraph context contains parent context justification when DParagraph justification is null"() {
        given:
        parentContext.textJustification >> Justification.CENTER

        expect:
        factory.createContextFrom(parentContext, new DParagraph()).textJustification.is Justification.CENTER
    }

    def "paragraph context contains parent context as parent"() {
        expect:

        factory.createContextFrom(parentContext, new DParagraph()).parent.is parentContext
    }
}