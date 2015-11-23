package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class PageWriterTest extends Specification {
    private PageWriter pageWriter
    private DContext pageContext = new DContext()
    private PageContextFactory pageContextFactory = Mock(PageContextFactory)
    private PagePdfBoxHelper pagePdfBoxHelper = Mock(PagePdfBoxHelper)
    private PageBoundsCalculator pageBoundsCalculator = Mock(PageBoundsCalculator)
    private PageContentsWriter pageContentsWriter = Mock(PageContentsWriter)
    private PageBorderDrawer pageBorderDrawer = Mock(PageBorderDrawer)

    def setup() {
        pageWriter = new PageWriter(
            pageContextFactory: pageContextFactory,
            pagePdfBoxHelper: pagePdfBoxHelper,
            pageBoundsCalculator: pageBoundsCalculator,
            pageContentsWriter: pageContentsWriter,
            pageBorderDrawer: pageBorderDrawer
        )
    }

    def "pageWriter write"() {
        given:
        DContext documentContext = new DContext()
        DPage dPage = new DPage()

        when:
        pageWriter.write(documentContext, dPage)

        then:
        1 * pageContextFactory.createPageContextFrom(sameInstance(documentContext), dPage) >> { pageContext }
        1 * pagePdfBoxHelper.initializePageObjects(sameInstance(pageContext))
        1 * pageBoundsCalculator.addCalculationsTo(sameInstance(pageContext), dPage)
        1 * pageContentsWriter.writeContents(dPage, sameInstance(pageContext))
        1 * pageBorderDrawer.drawFor(dPage, sameInstance(pageContext))
        1 * pagePdfBoxHelper.close(sameInstance(pageContext))
        0 * _
    }

}