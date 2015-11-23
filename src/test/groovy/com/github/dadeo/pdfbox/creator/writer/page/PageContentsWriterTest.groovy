package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance


class PageContentsWriterTest extends Specification {
    private static final DContext pageContext = new DContext()
    private PageObjectWriterFactory factory = Mock(PageObjectWriterFactory)
    private PageContentsWriter writer = new PageContentsWriter(writerFactory: factory)
    private DPage dPage = new DPage()

    def "passes null in to first child writer"() {
        DObject contents1 = new DObject() {}
        PageObjectWriter contentsWriter1 = Mock(PageObjectWriter)

        given:

        dPage.contents = [contents1]

        when:


        writer.writeContents(dPage, pageContext)

        then:

        1 * factory.createWriter(contents1) >> contentsWriter1
        1 * contentsWriter1.write(sameInstance(pageContext), contents1, null)
        0 * _
    }

    def "passes result of previous content writer in to next content writer"() {
        DObject contents1 = new DObject() {}
        DObject contents2 = new DObject() {}
        DObject contents3 = new DObject() {}
        PageObjectWriter contentsWriter1 = Mock(PageObjectWriter)
        PageObjectWriter contentsWriter2 = Mock(PageObjectWriter)
        PageObjectWriter contentsWriter3 = Mock(PageObjectWriter)
        PreviousElementDetails previousElementDetails1 = new PreviousElementDetails() {}
        PreviousElementDetails previousElementDetails2 = new PreviousElementDetails() {}
        PreviousElementDetails previousElementDetails3 = new PreviousElementDetails() {}

        given:

        dPage.contents = [contents1, contents2, contents3]

        when:


        writer.writeContents(dPage, pageContext)

        then:

        1 * factory.createWriter(sameInstance(contents1)) >> contentsWriter1
        1 * contentsWriter1.write(sameInstance(pageContext), contents1, null) >> previousElementDetails1

        1 * factory.createWriter(sameInstance(contents2)) >> contentsWriter2
        1 * contentsWriter2.write(sameInstance(pageContext), contents2, previousElementDetails1) >> previousElementDetails2

        1 * factory.createWriter(sameInstance(contents3)) >> contentsWriter3
        1 * contentsWriter3.write(sameInstance(pageContext), contents3, previousElementDetails2) >> previousElementDetails3

        0 * _
    }

}