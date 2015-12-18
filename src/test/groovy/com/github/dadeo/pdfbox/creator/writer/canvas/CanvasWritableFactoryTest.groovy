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
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.model.Canvas
import spock.lang.Specification

class CanvasWritableFactoryTest extends Specification {
    private ObjectContextFactory objectContextFactory = Mock(ObjectContextFactory)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private CanvasWritableFactory writableFactory = new CanvasWritableFactory(objectContextFactory: objectContextFactory,
                                                                              objectBoundsCalculator: objectBoundsCalculator)
    private DContext parentContext = Mock(DContext)
    private DContext childContext = Mock(DContext)
    private Canvas canvas = GroovyMock(Canvas)

    def "createFor"() {
        given:
        canvas.height >> 10
        1 * objectContextFactory.createContextFrom(parentContext, canvas) >> childContext
        1 * objectBoundsCalculator.resizeBoundsToHeight(10, childContext)

        when:
        CanvasWritable writable = writableFactory.createFor(parentContext, canvas)

        then:
        writable.canvas == canvas
        writable.context == childContext
    }

}