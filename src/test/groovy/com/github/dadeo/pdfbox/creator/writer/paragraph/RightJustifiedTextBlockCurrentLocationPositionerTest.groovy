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
import spock.lang.Specification


class RightJustifiedTextBlockCurrentLocationPositionerTest extends Specification {
    private static final float RIGHT = 600
    private static final float CURRENT_Y = 500
    private static final float LINE_WIDTH = 150
    private static final float LINE_HEIGHT = 12
    private RightJustifiedTextBlockCurrentLocationPositioner repositioner = new RightJustifiedTextBlockCurrentLocationPositioner()
    private AssignedLine line = Mock(AssignedLine)

    def "repositions the line to start below the point with x calculated by subtracting the line width from the right side"() {
        given:
        line.height >> LINE_HEIGHT
        line.width >> LINE_WIDTH

        expect:
        repositioner.repositionForLine(new Point(225, CURRENT_Y), new Bounds(800, RIGHT, 100, 72), line) == new Point((float) (RIGHT - LINE_WIDTH - 1), (float) (CURRENT_Y - LINE_HEIGHT))
    }

    def "repositioning for the next token increments the current lcoation by the width of the token"() {
        given:
        Point lastLocation = new Point(225, 500)
        StringToken token = new StringToken(size: 15)

        expect:
        repositioner.repositionForNextToken(token, lastLocation) == new Point(240, 500)
    }

}