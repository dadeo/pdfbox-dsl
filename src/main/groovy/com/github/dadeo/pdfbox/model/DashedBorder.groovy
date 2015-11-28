package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacing
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacingCalculator

class DashedBorder implements DBorder {
    BorderFragmentLengthAndSpacingCalculator calculator = new BorderFragmentLengthAndSpacingCalculator()

    /**
     * Number of fragments to have per top and bottom border.
     */
    int horizontalFragments = 5

    /**
     * Number of fragments to have per left and right border.
     */
    int verticalFragments = 5

    /**
     * Number of fragments to have per border.
     */
    void setFragments(int count) {
        horizontalFragments = count
        verticalFragments = count
    }

    /**
     * Length of fragments to have for the top and bottom vertical lines of the border. Default of 0 means the fragment
     * length should be calculated. Both fragment length and spacing can be set to be calculated. A runtime exception
     * will be thrown if both fragment length and spacing are specified.
     */
    float verticalFragmentLength = 0

    /**
     * Length of fragments to have for the left and right horizontal lines of the border. Default of 0 means the fragment
     * length should be calculated. Both fragment length and spacing can be set to be calculated. A runtime exception
     * will be thrown if both fragment length and spacing are specified.
     */
    float horizontalFragmentLength = 0

    /**
     * Length of fragments to have for the border. Default of 0 means the fragment lengths should be calculated. Both
     * fragment length and spacing can be set to be calculated. A runtime exception will be thrown if both fragment
     * length and spacing are specified.
     */
    void setFragmentLength(float length) {
        verticalFragmentLength = length
        horizontalFragmentLength = length
    }

    /**
     * Amount of spacing to have between fragments for the top and bottom vertical lines of the border. Default of 0
     * means the spacing should be calculated to match the fragment length. Both fragment length and spacing can be set
     * to be calculated. A runtime exception will be thrown if both fragment length and spacing are specified.
     */
    float verticalSpacingLength = 0

    /**
     * Amount of spacing to have between fragments for the left and right horizontal lines of the border. Default of 0
     * means the spacing should should be calculated to match the fragment length. Both fragment length and spacing can
     * be set to be calculated. A runtime exception will be thrown if both fragment length and spacing are specified.
     */
    float horizontalSpacingLength = 0

    /**
     * Amount of spacing to have between fragments of the border. Default of 0 means the spacing should match the
     * fragment length.  Both fragment length and spacing can be set to be calculated. A runtime exception will be
     * thrown if both fragment length and spacing are specified.
     */
    void setSpacingLength(float length) {
        horizontalSpacingLength = length
        verticalSpacingLength = length
    }

    void drawBorder(Bordered bordered, DWriter writer, DBounds bounds) {
        float top = bounds.top
        float right = bounds.right
        float bottom = bounds.bottom
        float left = bounds.left

        DBounds borderOffsets = bordered.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left

        if (bordered.borderTop != 0)
            drawHorizontalDashedLine(writer, new DPoint(left, offsetTop), new DPoint(right, offsetTop), bordered.borderTop)

        if (bordered.borderRight != 0)
            drawVerticalDashedLine(writer, new DPoint(offsetRight, top), new DPoint(offsetRight, bottom), bordered.borderRight)

        if (bordered.borderBottom != 0)
            drawHorizontalDashedLine(writer, new DPoint(left, offsetBottom), new DPoint(right, offsetBottom), bordered.borderBottom)

        if (bordered.borderLeft != 0)
            drawVerticalDashedLine(writer, new DPoint(offsetLeft, top), new DPoint(offsetLeft, bottom), bordered.borderLeft)
    }

    protected void drawVerticalDashedLine(DWriter writer, DPoint highPoint, DPoint lowPoint, float width) {
        int fragmentCount = verticalFragments
        float totalLength = highPoint.y - lowPoint.y
        BorderFragmentLengthAndSpacing lengths = calculator.calculate(totalLength, fragmentCount, verticalFragmentLength, verticalSpacingLength)
        DPoint start = highPoint
        DPoint end = highPoint.offsetY(-lengths.fragmentLength)
        fragmentCount.times {
            writer.drawLine(start, end, width)
            start = start.offsetY((float) -(lengths.spacingLength + lengths.fragmentLength))
            end = end.offsetY((float) -(lengths.spacingLength + lengths.fragmentLength))
        }
    }

    protected void drawHorizontalDashedLine(DWriter writer, DPoint leftPoint, DPoint rightPoint, float width) {
        int fragmentCount = horizontalFragments
        float totalLength = rightPoint.x - leftPoint.x
        BorderFragmentLengthAndSpacing lengths = calculator.calculate(totalLength, fragmentCount, horizontalFragmentLength, horizontalSpacingLength)
        DPoint start = leftPoint
        DPoint end = leftPoint.offsetX(lengths.fragmentLength)
        fragmentCount.times {
            writer.drawLine(start, end, width)
            start = start.offsetX((float) (lengths.spacingLength + lengths.fragmentLength))
            end = end.offsetX((float) (lengths.spacingLength + lengths.fragmentLength))
        }
    }

}