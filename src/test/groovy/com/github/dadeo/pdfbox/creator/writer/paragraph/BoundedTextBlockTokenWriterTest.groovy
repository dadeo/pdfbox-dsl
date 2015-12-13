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

import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class BoundedTextBlockTokenWriterTest extends Specification {
    private TextBlockCurrentLocationPositioner currentLocationRepositioner = Mock(TextBlockCurrentLocationPositioner)
    private BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
    private StringToken token = new StringToken()
    private DWriter dWriter = Mock(DWriter)
    private DFont font = Mock(DFont)

    def "writes token and returns the location to place next token"() {
        given:
        DPoint currentLocation = new DPoint(0, 1)
        DPoint postWriteRepositionedLocation = new DPoint(0, 3)

        token.font = font
        token.text = "yo dog"
        token.size = 8

        when:
        DPoint newLocation = tokenWriter.write(token, currentLocation, dWriter)

        then:
        newLocation == postWriteRepositionedLocation
        1 * currentLocationRepositioner.repositionForNextToken(token, currentLocation) >> postWriteRepositionedLocation
        1 * dWriter.writeText("yo dog", currentLocation, font)
    }

}