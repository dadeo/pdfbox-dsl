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
package com.github.dadeo.pdfbox.creator.writer.text

import com.github.dadeo.pdfbox.model.Font
import spock.lang.Specification

class AssignedLineTest extends Specification {

    def "height is the maximum height of the font"() {
        expect:
        List<StringToken> tokens = fontSizes.collect { new StringToken(font: new Font(size: it)) }
        new AssignedLine(tokens: tokens).height == height

        where:
        fontSizes    | height
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 7
        [5, 7, 3, 8] | 8
        [8, 7, 3, 5] | 8
    }

    def "width is the sum of all token widths added together"() {
        expect:
        List<StringToken> tokens = tokenWidths.collect { new StringToken(size: it) }
        new AssignedLine(tokens: tokens).width == width

        where:
        tokenWidths  | width
        []           | 0
        [5]          | 5
        [5, 7, 3]    | 15
        [5, 7, 3, 8] | 23
    }

    def "descent is the maximum descent of the font"() {
        given:
        List<StringToken> tokens = fontDescents.collect {
            StringToken token = Mock(StringToken)
            1 * token.descent >> it
            token
        }

        expect:
        new AssignedLine(tokens: tokens).descent == descent

        where:
        fontDescents     | descent
        []               | 0
        [-5]             | -5
        [-5, -7, -3]     | -7
        [-5, -7, -3, -8] | -8
        [-8, -7, -3, -5] | -8
    }

}