/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Page
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance


class PageBoundsCalculatorTest extends Specification {
    private static final Page CURRENT_PAGE = new Page()
    private static final Bounds BORDER_BOUNDS = new Bounds()
    private static final Bounds TEXT_BOUNDS = new Bounds(500, 0, 0, 72)

    private PageBoundsCalculations pageBoundsCalculations = Mock(PageBoundsCalculations)
    private PageBoundsCalculator calculator = new PageBoundsCalculator(pageBoundsCalculations: pageBoundsCalculations)
    private static final DContext pageContext = new DContext()

    def "addCalculationsTo adds text bounds to page context"() {
        given:

        1 * pageBoundsCalculations.calculatePageContentBounds(sameInstance(CURRENT_PAGE)) >> TEXT_BOUNDS

        when:

        calculator.addCalculationsTo(pageContext, CURRENT_PAGE)

        then:

        pageContext.contentsBounds.is TEXT_BOUNDS
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

}