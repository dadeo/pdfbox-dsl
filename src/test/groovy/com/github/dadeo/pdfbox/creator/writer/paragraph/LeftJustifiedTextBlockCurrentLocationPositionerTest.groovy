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


class LeftJustifiedTextBlockCurrentLocationPositionerTest extends Specification {
    private LeftJustifiedTextBlockCurrentLocationPositioner repositioner = new LeftJustifiedTextBlockCurrentLocationPositioner()
    private AssignedLine line = Mock(AssignedLine)

    def "repositions the line to start below the point justified to the left"() {
        given:
        line.height >> 12

        expect:
        repositioner.repositionForLine(new DPoint(225, 500), new DBounds(800, 600, 100, 72), line) == new DPoint(72, 488)
    }

    def "repositioning for next token increments the current location by the width of the token"() {
        given:
        DPoint lastLocation = new DPoint(225, 500)
        StringToken token = new StringToken(size: 15)

        expect:
        repositioner.repositionForNextToken(token, lastLocation) == new DPoint(240, 500)
    }

}