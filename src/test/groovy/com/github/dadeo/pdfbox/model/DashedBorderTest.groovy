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
import spock.lang.Specification

import java.awt.*

class DashedBorderTest extends Specification {
    private BorderFragmentLengthAndSpacingCalculator calculator = Mock(BorderFragmentLengthAndSpacingCalculator)
    private DashedBorder dashedBorder = new DashedBorder(calculator: calculator)
    private Bordered bordered = new Bordered() {}
    private DWriter writer = Mock(DWriter)

    def "bordered only has top border"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        bordered.borderTop = 6
        bordered.borderTopColor = Color.red
        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(401, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(199.5f, 797.5f), new DPoint(219.5f, 797.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(249.5f, 797.5f), new DPoint(269.5f, 797.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(299.5f, 797.5f), new DPoint(319.5f, 797.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has right border"() {
        given:
        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.borderRight = 6
        bordered.borderRightColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(701, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(597.5f, 800.5f), new DPoint(597.5f, 780.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(597.5f, 750.5f), new DPoint(597.5f, 730.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(597.5f, 700.5f), new DPoint(597.5f, 680.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has bottom border"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        bordered.borderBottom = 6
        bordered.borderBottomColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(401, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(199.5f, 102.5f), new DPoint(219.5f, 102.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(249.5f, 102.5f), new DPoint(269.5f, 102.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(299.5f, 102.5f), new DPoint(319.5f, 102.5f), 6, Color.red)
        0 * _
    }

    def "bordered only has left border"() {
        given:
        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.borderLeft = 6
        bordered.borderLeftColor = Color.red

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(701, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(202.5f, 800.5f), new DPoint(202.5f, 780.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(202.5f, 750.5f), new DPoint(202.5f, 730.5f), 6, Color.red)
        1 * writer.drawLine(new DPoint(202.5f, 700.5f), new DPoint(202.5f, 680.5f), 6, Color.red)
        0 * _
    }

    def "bordered has all sides"() {
        given:
        dashedBorder.horizontalFragments = 3
        dashedBorder.horizontalFragmentLength = 10
        dashedBorder.horizontalSpacingLength = 5

        dashedBorder.verticalFragments = 3
        dashedBorder.verticalFragmentLength = 10
        dashedBorder.verticalSpacingLength = 5

        bordered.border = 8

        when:
        dashedBorder.drawBorder(bordered, writer, new DBounds(800, 600, 100, 200))

        then:
        1 * calculator.calculate(401, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(199.5f, 796.5f), new DPoint(219.5f, 796.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(249.5f, 796.5f), new DPoint(269.5f, 796.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(299.5f, 796.5f), new DPoint(319.5f, 796.5f), 8, Color.black)

        1 * calculator.calculate(701, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(596.5f, 800.5f), new DPoint(596.5f, 780.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(596.5f, 750.5f), new DPoint(596.5f, 730.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(596.5f, 700.5f), new DPoint(596.5f, 680.5f), 8, Color.black)

        1 * calculator.calculate(401, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(199.5f, 103.5f), new DPoint(219.5f, 103.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(249.5f, 103.5f), new DPoint(269.5f, 103.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(299.5f, 103.5f), new DPoint(319.5f, 103.5f), 8, Color.black)

        1 * calculator.calculate(701, 3, 10, 5) >> new BorderFragmentLengthAndSpacing(20, 30)
        1 * writer.drawLine(new DPoint(203.5f, 800.5f), new DPoint(203.5f, 780.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(203.5f, 750.5f), new DPoint(203.5f, 730.5f), 8, Color.black)
        1 * writer.drawLine(new DPoint(203.5f, 700.5f), new DPoint(203.5f, 680.5f), 8, Color.black)

        0 * _
    }

}