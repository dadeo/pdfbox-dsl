package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.model.DBounds


interface ElementDetails {
    DBounds getContainingBounds()
}