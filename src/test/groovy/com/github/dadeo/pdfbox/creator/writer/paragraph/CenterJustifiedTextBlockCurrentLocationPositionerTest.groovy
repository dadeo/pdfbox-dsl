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
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class CenterJustifiedTextBlockCurrentLocationPositionerTest extends Specification {
    private static final float RIGHT = 600
    private static final float LEFT = 100
    private static final float CURRENT_Y = 500
    private static final float LINE_WIDTH = 150
    private static final float LINE_HEIGHT = 12
    private CenterJustifiedTextBlockCurrentLocationPositioner repositioner = new CenterJustifiedTextBlockCurrentLocationPositioner()
    private AssignedLine line = Mock(AssignedLine)

    def "repositions the line to start below the point with x calculated by subtracting half the line width from the center of the bounds"() {
        given:
        line.height >> LINE_HEIGHT
        line.width >> LINE_WIDTH

        expect:
        repositioner.repositionForLine(new DPoint(225, CURRENT_Y), new DBounds(800, RIGHT, 100, LEFT), line) == new DPoint(275, (float) (CURRENT_Y - LINE_HEIGHT))
    }

    def "repositioning for the next token increments the current lcoation by the width of the token"() {
        given:
        DPoint lastLocation = new DPoint(225, 500)
        StringToken token = new StringToken(size: 15)

        expect:
        repositioner.repositionForNextToken(token, lastLocation) == new DPoint(240, 500)
    }

}