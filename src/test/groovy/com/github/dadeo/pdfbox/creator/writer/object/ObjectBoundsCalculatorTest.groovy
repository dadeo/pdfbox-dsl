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
        context.contentsBounds.height == 150
    }

}