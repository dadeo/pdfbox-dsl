package com.github.dadeo.pdfbox.dsl

import org.junit.Test

class PdfMeasurementsTest {

    @Test
    void inch() {
        use(PdfMeasurements) {
            assert 0.5.inch == 36
            assert 1.inch == 72
            assert 2.inch == 144
        }
    }

    @Test
    void inches() {
        use(PdfMeasurements) {
            assert 0.5.inches == 36
            assert 1.inches == 72
            assert 2.inches == 144
        }
    }

    @Test
    void mm() {
        use(PdfMeasurements) {
            assert 12.7.mm == 36
            assert 25.4.mm == 72
            assert 50.8.mm == 144
        }
    }

    @Test
    void cm() {
        use(PdfMeasurements) {
            assert 1.27.cm == 36
            assert 2.54.cm == 72
            assert 5.08.cm == 144
        }
    }

}