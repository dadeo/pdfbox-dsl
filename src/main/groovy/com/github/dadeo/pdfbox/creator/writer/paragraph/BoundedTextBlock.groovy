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
package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import groovy.transform.Canonical

@Canonical
class BoundedTextBlock {
    List<AssignedLine> assignedLines = []
    float width
    float firstLineLeading
    float lineLeading

    float getHeight() {
        if (assignedLines) {
            float line1Leading = firstLineLeading
            float otherLinesLeading = (assignedLines.size() - 1) * lineLeading
            float lineHeights = assignedLines.inject(0) { a, b -> a + b.height }
            lineHeights + line1Leading + otherLinesLeading
        } else {
            0
        }
    }

    float getLastLineDescent() {
        assignedLines ? assignedLines[-1].descent : 0
    }
}