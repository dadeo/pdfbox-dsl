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
import spock.lang.Specification


class BoundedTextBlockTest extends Specification {
    private BoundedTextBlock boundedTextBlock = new BoundedTextBlock()

    def "height is calculated from the assigned lines"() {
        given:
        AssignedLine line1 = Mock(AssignedLine)
        AssignedLine line2 = Mock(AssignedLine)
        AssignedLine line3 = Mock(AssignedLine)
        boundedTextBlock.assignedLines = [line1, line2, line3]

        when:
        line1.height >> 10
        line2.height >> 5
        line3.height >> 12

        then:
        boundedTextBlock.height == 27
    }

    def "height includes lineLeading"() {
        given:
        AssignedLine line1 = Mock(AssignedLine)
        AssignedLine line2 = Mock(AssignedLine)
        AssignedLine line3 = Mock(AssignedLine)
        boundedTextBlock.assignedLines = [line1, line2, line3]
        boundedTextBlock.firstLineLeading = 5
        boundedTextBlock.lineLeading = 3

        when:
        line1.height >> 10
        line2.height >> 5
        line3.height >> 12

        then:
        boundedTextBlock.height == 38
    }

    def "height is 0 when no assigned lines"() {
        given:
        boundedTextBlock.assignedLines = []
        boundedTextBlock.firstLineLeading = 5
        boundedTextBlock.lineLeading = 5

        expect:
        boundedTextBlock.height == 0
    }

    def "lastLineDescent is the descent of the last line or zero"() {
        given:
        boundedTextBlock.assignedLines = lineDescent.collect {
            AssignedLine assignedLine = Mock(AssignedLine)
            assignedLine.descent >> it
            assignedLine
        }

        expect:
        boundedTextBlock.lastLineDescent == lastLineDescent

        where:
        lineDescent  | lastLineDescent
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 3
        [5, 7, 3, 8] | 8
        [8, 7, 3, 5] | 5
    }

}