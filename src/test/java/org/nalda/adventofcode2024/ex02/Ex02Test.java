package org.nalda.adventofcode2024.ex02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex02Test {
    @Test
    void testInputHas2SafeReports() {
        final Ex02 ex02 = new Ex02();

        assertThat(ex02.countSafeReports("ex02.input.txt")).isEqualTo(2);
    }

    @Test
    void firstLineIsSafe() {
        final Ex02.Report report = new Ex02.Report("7 6 4 2 1");
        assertThat(report.isSafe()).isTrue();
    }

    @Test
    void secondLineIsNotSafe() {
        final Ex02.Report report = new Ex02.Report("1 2 7 8 9");
        assertThat(report.isSafe()).isFalse();
    }
}
