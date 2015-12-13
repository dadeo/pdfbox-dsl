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

class LineBorder implements DBorder {

    void drawBorder(Bordered bordered, DWriter writer, DBounds bounds) {
        // the 0.5f adjustment is to make up for line centering
        float top = bounds.top + 0.5f
        float right = bounds.right + 0.5f
        float bottom = bounds.bottom - 0.5f
        float left = bounds.left - 0.5f

        DBounds borderOffsets = bordered.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left


        if (bordered.borderLeft != 0)
            writer.drawLine(new DPoint(offsetLeft, top), new DPoint(offsetLeft, bottom), bordered.borderLeft, bordered.borderLeftColor)

        if (bordered.borderRight != 0)
            writer.drawLine(new DPoint(offsetRight, top), new DPoint(offsetRight, bottom), bordered.borderRight, bordered.borderRightColor)

        if (bordered.borderTop != 0)
            writer.drawLine(new DPoint(left, offsetTop), new DPoint(right, offsetTop), bordered.borderTop, bordered.borderTopColor)

        if (bordered.borderBottom != 0)
            writer.drawLine(new DPoint(left, offsetBottom), new DPoint(right, offsetBottom), bordered.borderBottom, bordered.borderBottomColor)
    }

}