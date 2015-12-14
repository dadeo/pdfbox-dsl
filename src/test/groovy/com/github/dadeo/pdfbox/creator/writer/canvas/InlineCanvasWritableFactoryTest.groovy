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
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.InlineCanvas
import spock.lang.Specification

class InlineCanvasWritableFactoryTest extends Specification {
    private static final DBounds CONTAINING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ObjectContextFactory objectContextFactory = Mock(ObjectContextFactory)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private InlineCanvasWritableFactory writableFactory = new InlineCanvasWritableFactory(objectContextFactory: objectContextFactory,
                                                                                          objectBoundsCalculator: objectBoundsCalculator)
    private DContext parentContext = Mock(DContext)
    private DContext childContext = Mock(DContext)
    private InlineCanvas canvas = GroovyMock(InlineCanvas)

    def "createFor"() {
        given:
        childContext.containingBounds >> CONTAINING_BOUNDS
        canvas.height >> 10
        1 * objectContextFactory.createContextFrom(parentContext, canvas) >> childContext
        1 * objectBoundsCalculator.resizeBoundsToHeight(10, childContext)

        when:
        InlineCanvasWritable writable = writableFactory.createFor(parentContext, canvas, null)

        then:
        writable.canvas == canvas
        writable.context == childContext
        writable.elementDetails.containingBounds == CONTAINING_BOUNDS
    }

}