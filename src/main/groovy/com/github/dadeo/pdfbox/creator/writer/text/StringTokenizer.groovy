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