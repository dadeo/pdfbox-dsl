package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphWritableTest extends Specification {
    private BoundedTextBlockWriter boundedTextBlockWriter = Mock(BoundedTextBlockWriter)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private DContext paragraphContext = new DContext()
    private DParagraph paragraph = new DParagraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)
    private ElementDetails elementDetails = Mock(ElementDetails)

    def "write paragraph orchestration"() {
        given:
        ParagraphWritable paragraphWritable = new ParagraphWritable(paragraph, textBlock, paragraphContext, elementDetails)
        paragraphWritable.boundedTextBlockWriter = boundedTextBlockWriter
        paragraphWritable.borderDrawer = borderDrawer

        when:
        paragraphWritable.write()

        then:
        1 * boundedTextBlockWriter.write(textBlock, paragraphContext)
        1 * borderDrawer.drawFor(paragraph, paragraphContext)
    }

}