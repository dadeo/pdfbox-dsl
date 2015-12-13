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

import org.junit.Test

import java.awt.*

class BorderedTest {

    @Test
    void test_border_defaults() {
        Bordered bordered = new Bordered() {}

        assert bordered.borderTop == 0
        assert bordered.borderRight == 0
        assert bordered.borderBottom == 0
        assert bordered.borderLeft == 0
    }

    @Test
    void test_borderTop() {
        Bordered bordered = new Bordered() {}

        bordered.borderTop = 5

        assert bordered.borderTop == 5
        assert bordered.borderRight == 0
        assert bordered.borderBottom == 0
        assert bordered.borderLeft == 0
    }

    @Test
    void test_borderRight() {
        Bordered bordered = new Bordered() {}

        bordered.borderRight = 5

        assert bordered.borderTop == 0
        assert bordered.borderRight == 5
        assert bordered.borderBottom == 0
        assert bordered.borderLeft == 0
    }

    @Test
    void test_borderBottom() {
        Bordered bordered = new Bordered() {}

        bordered.borderBottom = 5

        assert bordered.borderTop == 0
        assert bordered.borderRight == 0
        assert bordered.borderBottom == 5
        assert bordered.borderLeft == 0
    }

    @Test
    void test_borderLeft() {
        Bordered bordered = new Bordered() {}

        bordered.borderLeft = 5

        assert bordered.borderTop == 0
        assert bordered.borderRight == 0
        assert bordered.borderBottom == 0
        assert bordered.borderLeft == 5
    }

    @Test
    void test_border_overrides_previous_values() {
        Bordered bordered = new Bordered() {}
        bordered.borderBottom = 3

        bordered.border = 5

        assert bordered.borderTop == 5
        assert bordered.borderRight == 5
        assert bordered.borderBottom == 5
        assert bordered.borderLeft == 5
    }

    @Test
    void test_borderColor_defaults() {
        Bordered bordered = new Bordered() {}

        assert bordered.borderTopColor == Color.black
        assert bordered.borderRightColor == Color.black
        assert bordered.borderBottomColor == Color.black
        assert bordered.borderLeftColor == Color.black
    }

    @Test
    void test_borderTopColor() {
        Bordered bordered = new Bordered() {}

        bordered.borderTopColor = Color.red

        assert bordered.borderTopColor == Color.red
        assert bordered.borderRightColor == Color.black
        assert bordered.borderBottomColor == Color.black
        assert bordered.borderLeftColor == Color.black
    }

    @Test
    void test_borderRightColor() {
        Bordered bordered = new Bordered() {}

        bordered.borderRightColor = Color.red

        assert bordered.borderTopColor == Color.black
        assert bordered.borderRightColor == Color.red
        assert bordered.borderBottomColor == Color.black
        assert bordered.borderLeftColor == Color.black
    }

    @Test
    void test_borderBottomColor() {
        Bordered bordered = new Bordered() {}

        bordered.borderBottomColor = Color.red

        assert bordered.borderTopColor == Color.black
        assert bordered.borderRightColor == Color.black
        assert bordered.borderBottomColor == Color.red
        assert bordered.borderLeftColor == Color.black
    }

    @Test
    void test_borderLeftColor() {
        Bordered bordered = new Bordered() {}

        bordered.borderLeftColor = Color.red

        assert bordered.borderTopColor == Color.black
        assert bordered.borderRightColor == Color.black
        assert bordered.borderBottomColor == Color.black
        assert bordered.borderLeftColor == Color.red
    }

    @Test
    void test_borderColor_overrides_previous_values() {
        Bordered bordered = new Bordered() {}

        bordered.borderColor = Color.red

        assert bordered.borderTopColor == Color.red
        assert bordered.borderRightColor == Color.red
        assert bordered.borderBottomColor == Color.red
        assert bordered.borderLeftColor == Color.red
    }


}