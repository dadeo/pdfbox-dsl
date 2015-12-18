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
package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Point


class CenterJustifiedTextBlockCurrentLocationPositioner implements TextBlockCurrentLocationPositioner {

    Point repositionForLine(Point currentLocation, Bounds contentsBounds, AssignedLine line) {
        float middleOfBounds = (contentsBounds.right - contentsBounds.left) / 2
        float halfOfLineWidth = line.width / 2
        new Point(x: contentsBounds.left + middleOfBounds - halfOfLineWidth, y: currentLocation.y - line.height)
    }

    Point repositionForNextToken(StringToken token, Point currentLocation) {
        new Point(x: currentLocation.x + token.size, y: currentLocation.y)
    }

}