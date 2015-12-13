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
package com.github.dadeo.pdfbox.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import groovy.transform.ToString


@CompileStatic
@Immutable
@ToString
class DBounds {
    float top, right, bottom, left

    DPoint leftTop() {
        new DPoint(left, top)
    }

    DPoint rightTop() {
        new DPoint(right, top)
    }

    DPoint leftBottom() {
        new DPoint(left, bottom)
    }

    DPoint rightBottom() {
        new DPoint(right, bottom)
    }

    float getWidth() {
        right - left + 1
    }

    float getHeight() {
        top - bottom + 1
    }

    DBounds offset(DBounds offsets) {
        new DBounds((float) (top + offsets.top), (float) (right + offsets.right), (float) (bottom + offsets.bottom), (float) (left + offsets.left))
    }

    DBounds offset(float offsetX, float offsetY) {
        new DBounds((float) (top + offsetY), (float) (right + offsetX), (float) (bottom + offsetY), (float) (left + offsetX))
    }

    DBounds offset(float topOffset, float rightOffset, float bottomOffset, float leftOffset) {
        new DBounds((float) (top + topOffset), (float) (right + rightOffset), (float) (bottom + bottomOffset), (float) (left + leftOffset))
    }

    static DBounds createFrom(DPoint leftTop, DPoint rightBottom) {
        float top = leftTop.y
        float right = rightBottom.x
        float bottom = rightBottom.y
        float left = leftTop.x

        new DBounds(top, right, bottom, left)
    }

}
