package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.StringToken
import groovy.transform.Canonical

@Canonical
class AssignedLine {
    List<StringToken> tokens = []
}