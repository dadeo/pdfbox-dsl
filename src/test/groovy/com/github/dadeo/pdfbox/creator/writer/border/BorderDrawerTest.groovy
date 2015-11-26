package com.github.dadeo.pdfbox.creator.writer.border

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DBounds
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class BorderDrawerTest extends Specification {
    private static final DBounds BORDER_BOUNDS = new DBounds(0, 0, 0, 0)
    private BorderDrawer drawer = new BorderDrawer()
    private LineBorder border = Mock(LineBorder)
    private Bordered bordered = new Bordered() {}
    private DWriter writer = new DWriter()
    private DContext pageContext = new DContext()

    def "requests border to draw itself"() {
        given:

        drawer.lineBorder = border
        pageContext.writer = writer
        pageContext.borderBounds = BORDER_BOUNDS

        when:

        drawer.drawFor(bordered, pageContext)

        then:

        1 * border.drawBorder(sameInstance(bordered), sameInstance(writer), sameInstance(BORDER_BOUNDS))
    }

}