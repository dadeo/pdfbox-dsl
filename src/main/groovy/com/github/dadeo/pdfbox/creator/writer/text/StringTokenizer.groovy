package com.github.dadeo.pdfbox.creator.writer.text

import com.github.dadeo.pdfbox.model.DFont
import groovy.transform.Immutable

@Immutable
class StringTokenizer {
    StringWidthCalculator calculator

    List<StringToken> tokenize(String text, DFont font) {
        List<StringToken> result = []
        int startPos = 0
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i)
            if (ch == ' ' as char || ch == '\n' as char) {
                if (startPos == i) {
                    result << createTokenFor(ch as String, font)
                } else {
                    String fragment = text.substring(startPos, i)
                    result << createTokenFor(fragment, font)
                    result << createTokenFor(ch as String, font)
                }
                startPos = i + 1
            }
        }

        if (startPos != text.length()) {
            String fragment = text.substring(startPos)
            result << createTokenFor(fragment, font)
        }

        result
    }

    private StringToken createTokenFor(String text, DFont font) {
        float width = text == '\n' ? 0 : calculator.calculateFor(text, font)
        new StringToken(text, width, font)
    }
}