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

import com.github.dadeo.pdfbox.creator.BootStrap

import java.awt.*

trait Bordered {

    float borderTop = 0
    float borderRight = 0
    float borderBottom = 0
    float borderLeft = 0

    DBorder borderPainter = BootStrap.lineBorder

    Color borderTopColor = Color.black
    Color borderRightColor = Color.black
    Color borderBottomColor = Color.black
    Color borderLeftColor = Color.black

    void setBorder(float newValue) {
        borderTop = newValue
        borderRight = newValue
        borderBottom = newValue
        borderLeft = newValue
        this
    }

    void setBorderColor(Color newColor) {
        borderTopColor = newColor
        borderRightColor = newColor
        borderBottomColor = newColor
        borderLeftColor = newColor
        this
    }

    DBounds getBorderTextOffsets() {
        new DBounds(-borderTop, -borderRight, borderBottom, borderLeft)
    }

    DBounds getBorderLineOffsets() {
        float topOffset = borderTop / 2
        float rightOffset = borderRight / 2
        float bottomOffset = borderBottom / 2
        float leftOffset = borderLeft / 2
        new DBounds(-topOffset, -rightOffset, bottomOffset, leftOffset)
    }
}