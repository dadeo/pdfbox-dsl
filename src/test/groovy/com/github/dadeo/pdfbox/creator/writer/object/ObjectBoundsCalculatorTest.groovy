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
package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.*
import spock.lang.Specification

class ObjectBoundsCalculatorTest extends Specification {
    private static final DBounds ORIGINAL_CONTAINING_BOUNDS = new DBounds(500, 600, 100, 72)
    private ObjectBoundsCalculator calculator = new ObjectBoundsCalculator()
    private DContext context = new DContext(containingBounds: ORIGINAL_CONTAINING_BOUNDS)

    def "calculate max bounds when no margin, no border, and no padding"() {
        given:
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS

        def object = new DObject() {}

        when:
        calculator.calculateMaxBounds(object, context)

        then:
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds == ORIGINAL_CONTAINING_BOUNDS
        context.contentsBounds == ORIGINAL_CONTAINING_BOUNDS
    }

    def "calculate max bounds when margin, no border, and no padding"() {
        given:
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS

        def object = new DObject() {} as Margined
        object.marginTop = 5
        object.marginRight = 10
        object.marginBottom = 15
        object.marginLeft = 20

        when:
        calculator.calculateMaxBounds(object, context)

        then:
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds == new DBounds(495, 590, 115, 92)
        context.contentsBounds == new DBounds(495, 590, 115, 92)
    }

    def "calculate max bounds when no margin, border, and no padding"() {
        given:
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS

        def object = new DObject() {} as Bordered
        object.borderTop = 5
        object.borderRight = 10
        object.borderBottom = 15
        object.borderLeft = 20

        when:
        calculator.calculateMaxBounds(object, context)

        then:
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds == new DBounds(500, 600, 100, 72)
        context.contentsBounds == new DBounds(495, 590, 115, 92)
    }

    def "calculate max bounds when no margin, no border, and padding"() {
        given:
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS

        def object = new DObject() {} as Padded
        object.paddingTop = 5
        object.paddingRight = 10
        object.paddingBottom = 15
        object.paddingLeft = 20

        when:
        calculator.calculateMaxBounds(object, context)

        then:
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds == ORIGINAL_CONTAINING_BOUNDS
        context.contentsBounds == new DBounds(495, 590, 115, 92)
    }

    def "calculate max bounds when margin, border, and padding"() {
        given:
        context.containingBounds = ORIGINAL_CONTAINING_BOUNDS

        def object = new DObject() {}.withTraits(Margined, Bordered, Padded)
        object.margin = 20
        object.border = 20
        object.padding = 20

        when:
        calculator.calculateMaxBounds(object, context)

        then:
        context.containingBounds == ORIGINAL_CONTAINING_BOUNDS
        context.borderBounds == new DBounds(480, 580, 120, 92)
        context.contentsBounds == new DBounds(440, 540, 160, 132)
    }

    def "resize bounds height"() {
        given:
        context.containingBounds = new DBounds(500, 600, 100, 72)
        context.borderBounds = new DBounds(480, 580, 120, 92)
        context.contentsBounds = new DBounds(440, 540, 160, 132)

        when:
        calculator.resizeBoundsToHeight(150, context)

        then:
        context.containingBounds == new DBounds(500, 600, 231, 72)
        context.borderBounds == new DBounds(480, 580, 251, 92)
        context.contentsBounds == new DBounds(440, 540, 291, 132)
        context.contentsBounds.height == 150f
    }

    def "resize bounds to content width"() {
        given:
        context.containingBounds = new DBounds(500, 600, 100, 72)
        context.borderBounds = new DBounds(480, 580, 120, 92)
        context.contentsBounds = new DBounds(440, 540, 160, 132)

        when:
        calculator.resizeBoundsToContentWidth(200, context)

        then:
        context.containingBounds == new DBounds(500, 391, 100, 72)
        context.borderBounds == new DBounds(480, 371, 120, 92)
        context.contentsBounds == new DBounds(440, 331, 160, 132)
        context.contentsBounds.width == 200f
    }

    def "move bounds horizontally"() {
        given:
        context.containingBounds = new DBounds(500, 600, 100, 72)
        context.borderBounds = new DBounds(480, 580, 120, 92)
        context.contentsBounds = new DBounds(440, 540, 160, 132)

        when:
        calculator.moveBoundsHorizontally(200, context)

        then:
        context.containingBounds == new DBounds(500, 800, 100, 272)
        context.borderBounds == new DBounds(480, 780, 120, 292)
        context.contentsBounds == new DBounds(440, 740, 160, 332)
    }

    def "shrink bounds vertically"() {
        given:
        context.containingBounds = new DBounds(500, 600, 100, 72)
        context.borderBounds = new DBounds(480, 580, 120, 92)
        context.contentsBounds = new DBounds(440, 540, 160, 132)

        when:
        calculator.shrinkBoundsVertically(200, context)

        then:
        context.containingBounds == new DBounds(300, 600, 100, 72)
        context.borderBounds == new DBounds(280, 580, 120, 92)
        context.contentsBounds == new DBounds(240, 540, 160, 132)
    }

}