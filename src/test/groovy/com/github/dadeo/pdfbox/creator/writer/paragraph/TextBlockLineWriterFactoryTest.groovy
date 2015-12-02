package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Justification
import spock.lang.Specification


class TextBlockLineWriterFactoryTest extends Specification {
    private TextBlockLineWriterFactory factory = BootStrap.textBlockLineWriterFactory
    private DContext context = new DContext()

    def "creates writer for left justified text"() {
        given:
        context.textJustification = Justification.LEFT

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.leftJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
    }

    def "creates writer for right justified text"() {
        given:
        context.textJustification = Justification.RIGHT

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.rightJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof RightJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof RightJustifiedTextBlockCurrentLocationPositioner
    }

    def "creates writer for center justified text"() {
        given:
        context.textJustification = Justification.CENTER

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.centerJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof CenterJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof CenterJustifiedTextBlockCurrentLocationPositioner
    }

    def "defaults to writer for left justified text when not specified"() {
        given:
        context.textJustification = null

        when:
        BoundedTextBlockLineWriter textBlockLineWriter = factory.createWriterFor(context)

        then:
        textBlockLineWriter == BootStrap.leftJustifiedTextBlockLineWriter
        textBlockLineWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
        textBlockLineWriter.tokenWriter.currentLocationPositioner instanceof LeftJustifiedTextBlockCurrentLocationPositioner
    }

}