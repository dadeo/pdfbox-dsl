package com.github.dadeo.pdfbox.model


class Table implements DObject, Margined, Bordered, Padded {
    List<Float> columnRatios
    List<Cell> contents = []

    Table(List<Float> columnRatios) {
        this.columnRatios = columnRatios
    }

    Table addCell(Cell cell) {
        contents << cell
        this
    }

    Table leftShift(Cell cell) {
        addCell(cell)
    }
}