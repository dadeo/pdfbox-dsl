package com.github.dadeo.pdfbox.creator.writer.text

import org.junit.Before
import org.junit.Test

import static groovy.test.GroovyAssert.shouldFail

class TokensToLineAssignerTest {
    private TokensToLineAssigner assigner

    @Before
    void setUp() {
        assigner = new TokensToLineAssigner()
    }

    @Test
    void test_assignToLine_single_token_fits_in_single_line() {
        StringToken token1 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1
                                                                     ],
                                                                     100,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1])
        ]
    }

    @Test
    void test_assignToLine_multiple_tokens_fits_in_single_line__boundary_condition() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 10)
        StringToken token3 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                     ],
                                                                     30,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1, token2, token3])
        ]
    }

    @Test
    void test_assignToLine_multiple_tokens_rolls_to_second_line__boundary_condition() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 10)
        StringToken token3 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token3]),
        ]
    }

    @Test
    void test_assignToLine_multiple_tokens_rolls_to_multiple_lines() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 10)
        StringToken token3 = new StringToken(size: 10)
        StringToken token4 = new StringToken(size: 10)
        StringToken token5 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token3, token4]),
            new AssignedLine([token5]),
        ]
    }

    @Test
    void test_assignToLine_newline_character_forces_following_tokens_to_a_new_line() {
        StringToken newLine = new StringToken(text: '\n', size: 1000)
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 10)
        StringToken token3 = new StringToken(size: 10)
        StringToken token4 = new StringToken(size: 10)
        StringToken token5 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         newLine,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1]),
            new AssignedLine([token2, token3]),
            new AssignedLine([token4, token5]),
        ]
    }

    @Test
    void test_assignToLine_spaces_before_newline_are_discarded() {
        StringToken space = new StringToken(size: 5, text: ' ')
        StringToken newLine = new StringToken(text: '\n', size: 1000)
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 10)
        StringToken token3 = new StringToken(size: 10)
        StringToken token4 = new StringToken(size: 10)
        StringToken token5 = new StringToken(size: 10)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         space,
                                                                         space,
                                                                         newLine,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1]),
            new AssignedLine([token2, token3]),
            new AssignedLine([token4, token5]),
        ]
    }

    @Test
    void test_assignToLine_word_doesnt_fit_first_line() {
        StringToken token1 = new StringToken(size: 30)

        String message = shouldFail {
            assigner.assignToLine([
                                      token1
                                  ],
                                  29,
                                  false)

        }

        assert message.contains('width')
        assert message.contains('not currently supported')
    }

    @Test
    void test_assignToLine_word_doesnt_fit_second_line() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 30)

        String message = shouldFail {
            assigner.assignToLine([
                                      token1,
                                      token2
                                  ],
                                  29,
                                  false)
        }

        assert message.contains('width')
        assert message.contains('not currently supported')
    }

    @Test
    void test_assignToLine_allowLineToStartWithSpaces_false_boundary_equals() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 19)
        StringToken token3 = new StringToken(size: 5, text: ' ')
        StringToken token4 = new StringToken(size: 5, text: ' ')
        StringToken token5 = new StringToken(size: 10)
        StringToken token6 = new StringToken(size: 19)
        StringToken token7 = new StringToken(size: 1)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                         token6,
                                                                         token7
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token5, token6]),
            new AssignedLine([token7]),
        ]
    }

    @Test
    void test_assignToLine_allowLineToStartWithSpaces_false_boundary_greater_than() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 18)
        StringToken token3 = new StringToken(size: 5, text: ' ')
        StringToken token4 = new StringToken(size: 5, text: ' ')
        StringToken token5 = new StringToken(size: 10)
        StringToken token6 = new StringToken(size: 19)
        StringToken token7 = new StringToken(size: 1)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                         token6,
                                                                         token7
                                                                     ],
                                                                     29,
                                                                     false)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token5, token6]),
            new AssignedLine([token7]),
        ]
    }

    @Test
    void test_assignToLine_allowLineToStartWithSpaces_true() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 19)
        StringToken token3 = new StringToken(size: 5, text: ' ')
        StringToken token4 = new StringToken(size: 5, text: ' ')
        StringToken token5 = new StringToken(size: 10)
        StringToken token6 = new StringToken(size: 9)
        StringToken token7 = new StringToken(size: 1)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                         token6,
                                                                         token7
                                                                     ],
                                                                     29,
                                                                     true)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token3, token4, token5, token6]),
            new AssignedLine([token7]),
        ]
    }

    @Test
    void test_assignToLine_line_is_not_allowed_to_end_with_spaces() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 17)
        StringToken token3 = new StringToken(size: 1, text: ' ')
        StringToken token4 = new StringToken(size: 1, text: ' ')
        StringToken token5 = new StringToken(size: 10)
        StringToken token6 = new StringToken(size: 9)
        StringToken token7 = new StringToken(size: 1)
        StringToken token8 = new StringToken(size: 1, text: ' ')
        StringToken token9 = new StringToken(size: 1, text: ' ')
        StringToken token10 = new StringToken(size: 1, text: ' ')
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                         token6,
                                                                         token7,
                                                                         token8,
                                                                         token9,
                                                                         token10,
                                                                     ],
                                                                     29,
                                                                     true)

        assert actualAssignments == [
            new AssignedLine([token1, token2]),
            new AssignedLine([token5, token6, token7])
        ]
    }

    @Test
    void test_assignToLine_multiple_spaces_allowed_in_line() {
        StringToken token1 = new StringToken(size: 10)
        StringToken token2 = new StringToken(size: 17)
        StringToken token3 = new StringToken(size: 1, text: ' ')
        StringToken token4 = new StringToken(size: 1, text: ' ')
        StringToken token5 = new StringToken(size: 1, text: ' ')
        StringToken token6 = new StringToken(size: 9)
        StringToken token7 = new StringToken(size: 1)
        StringToken token8 = new StringToken(size: 1, text: ' ')
        StringToken token9 = new StringToken(size: 1, text: ' ')
        StringToken token10 = new StringToken(size: 1, text: ' ')
        StringToken token11 = new StringToken(size: 1)
        List<AssignedLine> actualAssignments = assigner.assignToLine([
                                                                         token1,
                                                                         token2,
                                                                         token3,
                                                                         token4,
                                                                         token5,
                                                                         token6,
                                                                         token7,
                                                                         token8,
                                                                         token9,
                                                                         token10,
                                                                         token11,
                                                                     ],
                                                                     39,
                                                                     true)

        assert actualAssignments == [
            new AssignedLine([token1, token2, token3, token4, token5, token6]),
            new AssignedLine([token7, token8, token9, token10, token11])
        ]
    }

}