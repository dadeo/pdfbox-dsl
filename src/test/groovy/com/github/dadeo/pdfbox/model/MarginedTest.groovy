package com.github.dadeo.pdfbox.model

import org.junit.Test


class MarginedTest {

    @Test
    void test_defaults_to_zero() {
        Margined margined = new Margined() {}
        assert margined.marginTop == 0
        assert margined.marginRight == 0
        assert margined.marginBottom == 0
        assert margined.marginLeft == 0

    }

    @Test
    void test_setMarginTop() {
        Margined margined = new Margined() {}
        margined.marginTop = 3

        assert margined.marginTop == 3
        assert margined.marginRight == 0
        assert margined.marginBottom == 0
        assert margined.marginLeft == 0
    }

    @Test
    void test_setMarginRight() {
        Margined margined = new Margined() {}
        margined.marginRight = 3

        assert margined.marginTop == 0
        assert margined.marginRight == 3
        assert margined.marginBottom == 0
        assert margined.marginLeft == 0
    }

    @Test
    void test_setMarginBottom() {
        Margined margined = new Margined() {}
        margined.marginBottom = 3

        assert margined.marginTop == 0
        assert margined.marginRight == 0
        assert margined.marginBottom == 3
        assert margined.marginLeft == 0
    }

    @Test
    void test_setMarginLeft() {
        Margined margined = new Margined() {}
        margined.marginLeft = 3

        assert margined.marginTop == 0
        assert margined.marginRight == 0
        assert margined.marginBottom == 0
        assert margined.marginLeft == 3
    }

    @Test
    void test_setMargin_sets_all_margin_sides_to_the_new_value() {
        Margined margined = new Margined() {}
        margined.marginLeft = 2

        margined.margin = 5

        assert margined.marginTop == 5
        assert margined.marginRight == 5
        assert margined.marginBottom == 5
        assert margined.marginLeft == 5
    }

    @Test
    void test_getMarginOffsets() {
        Margined margined = new Margined() {}
        margined.marginTop = 1
        margined.marginRight = 2
        margined.marginBottom = 3
        margined.marginLeft = 4

        DBounds offsets = margined.marginOffsets

        assert offsets.top == -1
        assert offsets.right == -2
        assert offsets.bottom == 3
        assert offsets.left == 4
    }

}