package com.github.dadeo.pdfbox.creator.writer.text

import com.github.dadeo.pdfbox.model.DFont
import groovy.mock.interceptor.MockFor
import org.junit.Before
import org.junit.Test

class StringTokenizerTest {
    private static final DFont ACTUAL_FONT = new DFont()
    private StringTokenizer tokenizer
    private MockFor stringWidthCalculatorMock

    @Before
    void setUp() {
        stringWidthCalculatorMock = new MockFor(StringWidthCalculator)
    }

    private void finalizeSetUp() {
        tokenizer = new StringTokenizer(calculator: (StringWidthCalculator) stringWidthCalculatorMock.proxyInstance())
    }

    @Test
    void test_split_string_does_not_start_or_end_with_space() {
        String testString = "this is my test string"

        expectStringWidthCalculator expectsText: 'this', returns: 75
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'is', returns: 35
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'my', returns: 40
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'test', returns: 60
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'string', returns: 80

        finalizeSetUp()

        List<StringToken> actualTokens = tokenizer.tokenize(testString, ACTUAL_FONT)

        assert actualTokens == [
            new StringToken('this', 75, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('is', 35, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('my', 40, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('test', 60, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('string', 80, ACTUAL_FONT),
        ]
    }

    @Test
    void test_split_string_starts_with_space() {
        String testString = " this is my test string"

        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'this', returns: 75
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'is', returns: 35
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'my', returns: 40
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'test', returns: 60
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'string', returns: 80

        finalizeSetUp()

        List<StringToken> actualTokens = tokenizer.tokenize(testString, ACTUAL_FONT)

        assert actualTokens == [
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('this', 75, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('is', 35, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('my', 40, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('test', 60, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('string', 80, ACTUAL_FONT),
        ]
    }

    @Test
    void test_split_string_ends_with_space() {
        String testString = "this is my test string "

        expectStringWidthCalculator expectsText: 'this', returns: 75
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'is', returns: 35
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'my', returns: 40
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'test', returns: 60
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: 'string', returns: 80
        expectStringWidthCalculator expectsText: ' ', returns: 25

        finalizeSetUp()

        List<StringToken> actualTokens = tokenizer.tokenize(testString, ACTUAL_FONT)

        assert actualTokens == [
            new StringToken('this', 75, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('is', 35, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('my', 40, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('test', 60, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken('string', 80, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
        ]
    }

    @Test
    void test_split_multiple_spaces() {
        String testString = ' ' * 4

        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: ' ', returns: 25
        expectStringWidthCalculator expectsText: ' ', returns: 25

        finalizeSetUp()

        List<StringToken> actualTokens = tokenizer.tokenize(testString, ACTUAL_FONT)

        assert actualTokens == [
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
            new StringToken(' ', 25, ACTUAL_FONT),
        ]
    }

    private void expectStringWidthCalculator(Map<String, ?> options) {
        stringWidthCalculatorMock.demand.calculateFor { String partialText, DFont fontUsed ->
            assert partialText == options.expectsText
            assert fontUsed.is(ACTUAL_FONT)
            options.returns
        }
    }
}