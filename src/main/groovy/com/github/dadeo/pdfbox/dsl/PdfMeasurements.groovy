package com.github.dadeo.pdfbox.dsl


class PdfMeasurements {
    static final float POINTS_PER_INCH = 72f
    static final float CENTI_PER_INCH = 2.54
    static final float MILLIS_PER_INCH = 25.4

    static float getInch(Number number) {
        getInches(number)
    }

    static float getInches(Number number) {
        (number * POINTS_PER_INCH).toFloat()
    }

    static float getMm(Number number) {
        (number * POINTS_PER_INCH / MILLIS_PER_INCH).toFloat()
    }

    static float getCm(Number number) {
        (number * POINTS_PER_INCH / CENTI_PER_INCH).toFloat()
    }

}