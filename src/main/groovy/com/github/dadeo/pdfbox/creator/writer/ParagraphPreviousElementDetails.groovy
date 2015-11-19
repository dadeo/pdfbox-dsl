package com.github.dadeo.pdfbox.creator.writer

import groovy.transform.Immutable


@Immutable
class ParagraphPreviousElementDetails implements PreviousElementDetails {
    float lastLineDescent
    boolean hasBottomBorder
}