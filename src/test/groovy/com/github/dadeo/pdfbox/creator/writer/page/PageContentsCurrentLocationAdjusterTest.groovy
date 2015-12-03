package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class PageContentsCurrentLocationAdjusterTest extends Specification {
    private static final DBounds CONTENTS_BOUNDS = new DBounds(500, 600, 150, 120)
    private static final DBounds UPDATED_CONTENTS_BOUNDS = new DBounds(349, 600, 150, 120)
    private PageContentsCurrentLocationAdjuster adjuster = new PageContentsCurrentLocationAdjuster()

    def "adjusts the current location to the bottom bounds of the previous element"() {
        given:
        DContext pageContext = new DContext(contentsBounds: CONTENTS_BOUNDS)
        ElementDetails previousElementDetails = Mock(ElementDetails)
        1 * previousElementDetails.containingBounds >> new DBounds(500, 550, 350, 120)

        when:
        adjuster.adjust(pageContext, previousElementDetails)

        then:
        pageContext.contentsBounds == UPDATED_CONTENTS_BOUNDS
    }

}