package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.StringTokenizer
import com.github.dadeo.pdfbox.creator.StringWidthCalculator
import com.github.dadeo.pdfbox.creator.TokensToLineAssigner


class BootStrap {
    static final StringWidthCalculator stringWidthCalculator
    static final TokensToLineAssigner tokensToLineAssigner
    static final StringTokenizer stringTokenizer

    static {
        stringWidthCalculator = new StringWidthCalculator()
        tokensToLineAssigner = new TokensToLineAssigner()
        stringTokenizer = new StringTokenizer(calculator: stringWidthCalculator)
    }

}