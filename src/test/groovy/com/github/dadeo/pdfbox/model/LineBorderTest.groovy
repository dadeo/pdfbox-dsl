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
import spock.lang.Specification

import java.awt.*

class LineBorderTest extends Specification {
    private LineBorder lineBorder = new LineBorder()
    private Bordered bordered = new Bordered() {}
    private DWriter writer = Mock(DWriter)

    def "bordered only has top border"() {
        given:
        bordered.borderTop = 6
        bordered.borderTopColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new Bounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new Point(99.5f, 97.5f), new Point(200.5f, 97.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has right border"() {
        given:
        bordered.borderRight = 6
        bordered.borderRightColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new Bounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new Point(197.5F, 100.5F), new Point(197.5F, -0.5F), 6, Color.red)
        0 * _
    }

    def "bordered only has bottom border"() {
        given:
        bordered.borderBottom = 6
        bordered.borderBottomColor = Color.red

        when:
        lineBorder.drawBorder(bordered, writer, new Bounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new Point(99.5f, 2.5f), new Point(200.5f, 2.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has left border"() {
        given:
        bordered.borderLeft = 6
        bordered.borderLeftColor = Color.red
        when:
        lineBorder.drawBorder(bordered, writer, new Bounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new Point(102.5f, 100.5f), new Point(102.5f, -0.5f), 6, Color.red)
        0 * _
    }

    def "bordered has all sides"() {
        given:
        bordered.border = 8

        when:
        lineBorder.drawBorder(bordered, writer, new Bounds(100, 200, 0, 100))

        then:
        1 * writer.drawLine(new Point(99.5f, 96.5f), new Point(200.5f, 96.5f), 8, Color.black)    // top
        1 * writer.drawLine(new Point(196.5f, 100.5f), new Point(196.5f, -0.5f), 8, Color.black)  // right
        1 * writer.drawLine(new Point(99.5f, 3.5f), new Point(200.5f, 3.5f), 8, Color.black)      // bottom
        1 * writer.drawLine(new Point(103.5f, 100.5f), new Point(103.5f, -0.5f), 8, Color.black)  // left
        0 * _
    }

}