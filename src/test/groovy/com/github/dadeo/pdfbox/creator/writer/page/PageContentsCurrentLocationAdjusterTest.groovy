package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class PageContentsCurrentLocationAdjusterTest extends Specification {
    private PageContentsCurrentLocationAdjuster adjuster = new PageContentsCurrentLocationAdjuster()

    def "adjusts the current location to the bottom bounds of the previous element"() {
        given:
        DContext pageContext = new DContext()
        ElementDetails previousElementDetails = Mock(ElementDetails)
        1 * previousElementDetails.containingBounds >> new DBounds(500, 600, 350, 120)

        when:
        adjuster.adjust(pageContext, previousElementDetails)

        then:
        pageContext.currentLocation == new DPoint(120, 350)
    }

}