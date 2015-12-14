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
package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Cell


class CellWritable implements ObjectWritable {
    BackgroundPainter backgroundPainter = BootStrap.backgroundPainter
    BorderDrawer borderDrawer = BootStrap.borderDrawer
    Cell cell
    DContext context
    ElementDetails elementDetails
    List<ObjectWritable> contents

    @Override
    void write() {
        backgroundPainter.paintFor(context)

        contents.each {
            it.write()
        }

        borderDrawer.drawFor(cell, context)
    }

    @Override
    void offset(float x, float y) {
//        ObjectWritable.super.offset(x, y)
        contents*.offset(x, y)
    }

    float getHeight() {
        context.containingBounds.height
    }
}