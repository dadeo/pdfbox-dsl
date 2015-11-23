package com.github.dadeo.pdfbox.model

import org.junit.Test


class PaddedTest {

    @Test
    void test_defaults_to_zero() {
        Padded padded = new Padded() {}
        assert padded.paddingTop == 0
        assert padded.paddingRight == 0
        assert padded.paddingBottom == 0
        assert padded.paddingLeft == 0

    }

    @Test
    void test_setPaddingTop() {
        Padded padded = new Padded() {}
        padded.paddingTop = 3

        assert padded.paddingTop == 3
        assert padded.paddingRight == 0
        assert padded.paddingBottom == 0
        assert padded.paddingLeft == 0
    }

    @Test
    void test_setPaddingRight() {
        Padded padded = new Padded() {}
        padded.paddingRight = 3

        assert padded.paddingTop == 0
        assert padded.paddingRight == 3
        assert padded.paddingBottom == 0
        assert padded.paddingLeft == 0
    }

    @Test
    void test_setPaddingBottom() {
        Padded padded = new Padded() {}
        padded.paddingBottom = 3

        assert padded.paddingTop == 0
        assert padded.paddingRight == 0
        assert padded.paddingBottom == 3
        assert padded.paddingLeft == 0
    }

    @Test
    void test_setPaddingLeft() {
        Padded padded = new Padded() {}
        padded.paddingLeft = 3

        assert padded.paddingTop == 0
        assert padded.paddingRight == 0
        assert padded.paddingBottom == 0
        assert padded.paddingLeft == 3
    }

    @Test
    void test_setPadding_sets_all_padding_sides_to_the_new_value() {
        Padded padded = new Padded() {}
        padded.paddingLeft = 2

        padded.padding = 5

        assert padded.paddingTop == 5
        assert padded.paddingRight == 5
        assert padded.paddingBottom == 5
        assert padded.paddingLeft == 5
    }

    @Test
    void test_getPaddingOffsets() {
        Padded padded = new Padded() {}
        padded.paddingTop = 1
        padded.paddingRight = 2
        padded.paddingBottom = 3
        padded.paddingLeft = 4

        DBounds offsets = padded.paddingOffsets

        assert offsets.top == -1
        assert offsets.right == -2
        assert offsets.bottom == 3
        assert offsets.left == 4
    }

}