package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.model.DFont
import groovy.transform.Immutable

@Immutable
class StringTokenizer {
    StringWidthCalculator calculator

    List<StringToken> tokenize(String text, DFont font) {
        List<StringToken> result = []
        int startPos = 0
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == ' ' as char) {
                if (startPos == i) {
                    String fragment = ' '
                    result << new StringToken(fragment, calculator.calculateFor(fragment, font), font)
                } else {
                    String fragment = text.substring(startPos, i)
                    result << new StringToken(fragment, calculator.calculateFor(fragment, font), font)
                    result << new StringToken(' ', calculator.calculateFor(' ', font), font)
                }
                startPos = i + 1
            }
        }

        if (startPos != text.length()) {
            String fragment = text.substring(startPos)
            result << new StringToken(fragment, calculator.calculateFor(fragment, font), font)
        }

        result
    }
}