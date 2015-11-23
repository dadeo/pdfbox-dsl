package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance


class PageBoundsCalculatorTest extends Specification {
    private static final DPage CURRENT_PAGE = new DPage()
    public static final DBounds BORDER_BOUNDS = new DBounds()
    public static final DBounds TEXT_BOUNDS = new DBounds(500, 0, 0, 72)

    private PageBoundsCalculations pageBoundsCalculations = Mock(PageBoundsCalculations)
    private PageBoundsCalculator calculator = new PageBoundsCalculator(pageBoundsCalculations: pageBoundsCalculations)
    private static final DContext pageContext = new DContext()

    def "addCalculationsTo adds text bounds to page context"() {
        given:

        1 * pageBoundsCalculations.calculatePageContentBounds(sameInstance(CURRENT_PAGE)) >> TEXT_BOUNDS

        when:

        calculator.addCalculationsTo(pageContext, CURRENT_PAGE)

        then:

        pageContext.bounds.is TEXT_BOUNDS
    }

    def "addCalculationsTo adds border bounds to page context"() {
        given:

        1 * pageBoundsCalculations.calculatePageBorderBounds(sameInstance(CURRENT_PAGE)) >> BORDER_BOUNDS
        1 * pageBoundsCalculations.calculatePageContentBounds(sameInstance(CURRENT_PAGE)) >> TEXT_BOUNDS

        when:

        calculator.addCalculationsTo(pageContext, CURRENT_PAGE)

        then:

        pageContext.borderBounds.is BORDER_BOUNDS
    }

    def "addCalculationsTo sets current location to top-left of text bounds"() {
        given:

        1 * pageBoundsCalculations.calculatePageContentBounds(sameInstance(CURRENT_PAGE)) >> TEXT_BOUNDS

        when:

        calculator.addCalculationsTo(pageContext, CURRENT_PAGE)

        then:

        pageContext.currentLocation == TEXT_BOUNDS.leftTop()
    }

}