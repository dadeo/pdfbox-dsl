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
package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.Bounds
import spock.lang.Specification

import java.awt.*

class BackgroundPainterTest extends Specification {
    private static final Bounds BORDER_BOUNDS = new Bounds(1, 2, 3, 4)
    private static final Bounds ADJUSTED_BOUNDS = new Bounds(0.5f, 1.5f, 2.5f, 3.5f)
    private DWriter writer = Mock(DWriter)
    private DContext context = new DContext(writer: writer)
    private BackgroundPainter painter = new BackgroundPainter()

    def "paintFor fills a rectangle when a backgroundColor is present"() {
        given:
        context.backgroundColor = Color.BLACK
        context.borderBounds = BORDER_BOUNDS

        when:
        painter.paintFor(context)

        then:
        1 * writer.fillRectangle(ADJUSTED_BOUNDS, Color.black)
    }

    def "paintFor does nothing when no backgroundColor is present"() {
        given:
        context.backgroundColor = null
        context.borderBounds = BORDER_BOUNDS

        when:
        painter.paintFor(context)

        then:
        0 * _
    }

}