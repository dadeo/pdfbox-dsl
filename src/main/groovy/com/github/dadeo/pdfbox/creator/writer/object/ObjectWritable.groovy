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

trait ObjectWritable {

    abstract void write()

    abstract DContext getContext()

    void offset(float x, float y) {
        DContext context = getContext()
        context.containingBounds = context.containingBounds.offset(y, x, y, x)
        context.borderBounds = context.borderBounds.offset(y, x, y, x)
        context.contentsBounds = context.contentsBounds.offset(y, x, y, x)
    }
}