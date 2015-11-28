package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter


interface DBorder {

    void drawBorder(Bordered bordered, DWriter writer, DBounds bounds)

}