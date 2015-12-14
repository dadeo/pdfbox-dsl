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
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.InlineCanvas

class InlineCanvasWritableFactory implements ObjectWritableFactory<InlineCanvas> {
    ObjectContextFactory objectContextFactory
    ObjectBoundsCalculator objectBoundsCalculator

    ObjectWritable createFor(DContext parentContext, InlineCanvas canvas, ElementDetails previousElementDetails) {
        DContext canvasContext = objectContextFactory.createContextFrom(parentContext, canvas)
        objectBoundsCalculator.resizeBoundsToHeight(canvas.height, canvasContext)
        new InlineCanvasWritable(canvas, canvasContext, new InlineCanvasElementDetails(containingBounds: canvasContext.containingBounds))
    }

}