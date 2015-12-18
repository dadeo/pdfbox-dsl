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
package com.github.dadeo.pdfbox.creator.writer.canvas

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.model.Canvas
import com.github.dadeo.pdfbox.model.Bounds
import spock.lang.Specification

class CanvasWritableTest extends Specification {
    private static final Bounds CONTENTS_BOUNDS = new Bounds(1, 2, 3, 4)
    private BackgroundPainter backgroundPainter = Mock(BackgroundPainter)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private Canvas canvas = Mock(Canvas)
    private DContext context = new DContext()
    private DWriter writer = Mock(DWriter)
    private CanvasWritable writable = new CanvasWritable(canvas: canvas,
                                                         context: context,
                                                         backgroundPainter: backgroundPainter,
                                                         borderDrawer: borderDrawer)

    def "write"() {
        given:
        context.contentsBounds = CONTENTS_BOUNDS
        context.writer = writer

        when:
        writable.write()

        then:
        1 * backgroundPainter.paintFor(context)
        1 * canvas.draw(CONTENTS_BOUNDS, writer)
        1 * borderDrawer.drawFor(canvas, context)
    }

}