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

import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Page
import spock.lang.Specification


class PageBoundsCalculationsTest extends Specification {
    private PageBoundsCalculations calculations = new PageBoundsCalculations()
    private Page page = new Page(pageBounds: new Bounds(720, 600, 200, 100))

    def "border bounds when no margin, borders, and spacing"() {
        expect:

        calculations.calculatePageBorderBounds(page) == page.pageBounds
    }

    def "border bounds with only margin"() {
        given:

        page.margin = 10

        expect:

        calculations.calculatePageBorderBounds(page) == new Bounds(710, 590, 210, 110)
    }

    def "border bounds with every margin a different value"() {
        given:

        page.marginTop = 10
        page.marginRight = 20
        page.marginBottom = 30
        page.marginLeft = 40

        expect:

        calculations.calculatePageBorderBounds(page) == new Bounds(710, 580, 230, 140)
    }

    def "border bounds are unaffected by width of border"() {
        given:

        page.border = 10

        expect:

        calculations.calculatePageBorderBounds(page) == new Bounds(720, 600, 200, 100)
    }

    def "border bounds are not affected by padding"() {
        given:

        page.padding = 10

        expect:

        calculations.calculatePageBorderBounds(page) == new Bounds(720, 600, 200, 100)
    }

    def "content bounds when no margin, borders, and spacing"() {
        expect:

        calculations.calculatePageContentBounds(page) == page.pageBounds
    }

    def "contents are within margin"() {
        given:

        page.margin = 10

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 590, 210, 110)
    }

    def "contents with every margin a different value"() {
        given:

        page.marginTop = 10
        page.marginRight = 20
        page.marginBottom = 30
        page.marginLeft = 40

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 580, 230, 140)
    }

    def "contents are within border"() {
        given:

        page.border = 10

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 590, 210, 110)
    }

    def "contents with every border a different value"() {
        given:

        page.borderTop = 10
        page.borderRight = 20
        page.borderBottom = 30
        page.borderLeft = 40

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 580, 230, 140)
    }

    def "contents are within padding"() {
        given:

        page.padding = 10

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 590, 210, 110)
    }

    def "contents with every padding a different value"() {
        given:

        page.paddingTop = 10
        page.paddingRight = 20
        page.paddingBottom = 30
        page.paddingLeft = 40

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(710, 580, 230, 140)
    }

    def "margin, border, and padding are accumulated for content's bounds"() {
        given:

        page.margin = 5
        page.border = 10
        page.padding = 15

        expect:

        calculations.calculatePageContentBounds(page) == new Bounds(690, 570, 230, 130)
    }

}