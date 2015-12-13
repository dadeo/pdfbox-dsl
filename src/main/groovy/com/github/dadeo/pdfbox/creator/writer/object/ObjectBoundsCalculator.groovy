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
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.Margined
import com.github.dadeo.pdfbox.model.Padded

class ObjectBoundsCalculator {

    /**
     * Calculates the maximum size the object's border and contents bounds can be to fit in the given containgBounds.
     * The context specifies the containingBounds to use for the calculations. The object specifies the margins,
     * borders, and padding to use for the calculations. Only the borderBounds and contentsBounds are updated.
     * @param object the object to calculate the bounds for
     * @param context the context with the containingBounds, borderBounds, and contentsBounds
     */
    void calculateMaxBounds(DObject object, DContext context) {
        context.borderBounds = context.containingBounds

        if (object instanceof Margined)
            context.borderBounds = context.borderBounds
                                          .offset(object.marginOffsets)

        context.contentsBounds = context.borderBounds

        if (object instanceof Bordered)
            context.contentsBounds = context.contentsBounds
                                            .offset(object.borderTextOffsets)

        if (object instanceof Padded)
            context.contentsBounds = context.contentsBounds
                                            .offset(object.paddingOffsets)
    }

    /**
     * Resizing the bounds to a new height changes the bottom of the bounds.  This is in contrast to shrinking the
     * bounds which moves the top of the bounds.  The containingBounds, borderBounds, and contentsBounds are updated.
     * @param height the new height of the contents bounds
     * @param context the context with the containingBounds, borderBounds, and contentsBounds to alter
     */
    void resizeBoundsToHeight(float height, DContext context) {
        float oldHeight = context.contentsBounds.height
        float adjustment = oldHeight - height
        context.containingBounds = context.containingBounds.offset(0, 0, adjustment, 0)
        context.borderBounds = context.borderBounds.offset(0, 0, adjustment, 0)
        context.contentsBounds = context.contentsBounds.offset(0, 0, adjustment, 0)
    }

    /**
     * Resizing the bounds to a new width changes the right of the bounds.  The containingBounds, borderBounds,
     * and contentsBounds are updated.
     * @param width the new width of the contents bounds
     * @param context the context with the containingBounds, borderBounds, and contentsBounds to alter
     */
    void resizeBoundsToContentWidth(float width, DContext context) {
        float currentWidth = context.contentsBounds.width
        float adjustment = currentWidth - width
        context.containingBounds = context.containingBounds.offset(0, -adjustment, 0, 0)
        context.borderBounds = context.borderBounds.offset(0, -adjustment, 0, 0)
        context.contentsBounds = context.contentsBounds.offset(0, -adjustment, 0, 0)
    }

    /**
     * Moving the bounds a given offset. This affects both the left and ride sides of the bounds.  The containingBounds,
     * borderBounds, and contentsBounds are updated.
     * @param offset the amount of distance to move the bounds to the right
     * @param context the context with the containingBounds, borderBounds, and contentsBounds to alter
     */
    void moveBoundsHorizontally(float offset, DContext context) {
        context.containingBounds = context.containingBounds.offset(0, offset, 0, offset)
        context.borderBounds = context.borderBounds.offset(0, offset, 0, offset)
        context.contentsBounds = context.contentsBounds.offset(0, offset, 0, offset)
    }

    /**
     * Shrinking the bounds by an amount lowers the top of the bounds.  This is in contrast to resizing the bounds to a
     * new height which changes the bottom of the bounds. The containingBounds, borderBounds, and contentsBounds are
     * updated.
     * @param amount to lower the top of the bounds by.
     * @param context the context with the containingBounds, borderBounds, and contentsBounds to alter
     */
    void shrinkBoundsVertically(float amount, DContext context) {
        context.containingBounds = context.containingBounds.offset(-amount, 0, 0, 0)
        context.borderBounds = context.borderBounds.offset(-amount, 0, 0, 0)
        context.contentsBounds = context.contentsBounds.offset(-amount, 0, 0, 0)
    }

}