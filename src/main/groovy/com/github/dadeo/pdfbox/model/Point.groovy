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

import groovy.transform.AutoClone
import groovy.transform.Immutable


@Immutable
@AutoClone
class Point {
    float x, y

    Point offsetX(float xAdjustment) {
        new Point((float) (x + xAdjustment), y)
    }

    Point offsetY(float yAdjustment) {
        new Point(x, (float) (y + yAdjustment))
    }

    Point offset(Point offsets) {
        new Point((float) (x + offsets.x), (float) (y + offsets.y))
    }

    Point offset(float offsetX, float offsetY) {
        new Point((float) (x + offsetX), (float) (y + offsetY))
    }
}
