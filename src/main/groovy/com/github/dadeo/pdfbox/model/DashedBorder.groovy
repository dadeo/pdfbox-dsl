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
package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacing
import com.github.dadeo.pdfbox.model.support.BorderFragmentLengthAndSpacingCalculator

import java.awt.*

class DashedBorder implements Border {
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

    void drawBorder(Bordered bordered, DWriter writer, Bounds bounds) {
        // the 0.5f adjustment is to make up for line centering
        float top = bounds.top + 0.5f
        float right = bounds.right + 0.5f
        float bottom = bounds.bottom - 0.5f
        float left = bounds.left - 0.5f

        Bounds borderOffsets = bordered.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left

        if (bordered.borderLeft != 0)
            drawVerticalDashedLine(writer, new Point(offsetLeft, top), new Point(offsetLeft, bottom), bordered.borderLeft, bordered.borderLeftColor)

        if (bordered.borderRight != 0)
            drawVerticalDashedLine(writer, new Point(offsetRight, top), new Point(offsetRight, bottom), bordered.borderRight, bordered.borderRightColor)

        if (bordered.borderTop != 0)
            drawHorizontalDashedLine(writer, new Point(left, offsetTop), new Point(right, offsetTop), bordered.borderTop, bordered.borderTopColor)

        if (bordered.borderBottom != 0)
            drawHorizontalDashedLine(writer, new Point(left, offsetBottom), new Point(right, offsetBottom), bordered.borderBottom, bordered.borderBottomColor)
    }

    protected void drawVerticalDashedLine(DWriter writer, Point highPoint, Point lowPoint, float width, Color color) {
        int fragmentCount = verticalFragments
        float totalLength = highPoint.y - lowPoint.y
        BorderFragmentLengthAndSpacing lengths = calculator.calculate(totalLength, fragmentCount, verticalFragmentLength, verticalSpacingLength)
        Point start = highPoint
        Point end = highPoint.offsetY(-lengths.fragmentLength)
        fragmentCount.times {
            writer.drawLine(start, end, width, color)
            start = start.offsetY((float) -(lengths.spacingLength + lengths.fragmentLength))
            end = end.offsetY((float) -(lengths.spacingLength + lengths.fragmentLength))
        }
    }

    protected void drawHorizontalDashedLine(DWriter writer, Point leftPoint, Point rightPoint, float width, Color color) {
        int fragmentCount = horizontalFragments
        float totalLength = rightPoint.x - leftPoint.x
        BorderFragmentLengthAndSpacing lengths = calculator.calculate(totalLength, fragmentCount, horizontalFragmentLength, horizontalSpacingLength)
        Point start = leftPoint
        Point end = leftPoint.offsetX(lengths.fragmentLength)
        fragmentCount.times {
            writer.drawLine(start, end, width, color)
            start = start.offsetX((float) (lengths.spacingLength + lengths.fragmentLength))
            end = end.offsetX((float) (lengths.spacingLength + lengths.fragmentLength))
        }
    }

}