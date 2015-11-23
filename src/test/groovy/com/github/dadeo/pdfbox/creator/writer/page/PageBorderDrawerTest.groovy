package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.border.LineBorder
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance


class PageBorderDrawerTest extends Specification {
    private static final DBounds BORDER_BOUNDS = new DBounds(0, 0, 0, 0)
    private PageBorderDrawer drawer = new PageBorderDrawer()
    private LineBorder border = Mock(LineBorder)
    private DPage dPage = new DPage()
    private DWriter writer = new DWriter()
    private DContext pageContext = new DContext()

    def "requests border to draw itself"() {
        given:

        drawer.lineBorder = border
        pageContext.writer = writer
        pageContext.borderBounds = BORDER_BOUNDS

        when:

        drawer.drawFor(dPage, pageContext)

        then:

        1 * border.drawBorder(sameInstance(dPage), sameInstance(writer), sameInstance(BORDER_BOUNDS))
    }

}