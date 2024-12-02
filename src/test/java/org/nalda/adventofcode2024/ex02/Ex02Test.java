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
        final Ex02.Report report = Ex02.Report.createReport("7 6 4 2 1");
        assertThat(report.isSafe()).isTrue();
    }

    @Test
    void secondLineIsNotSafe() {
        final Ex02.Report report = Ex02.Report.createReport("1 2 7 8 9");
        assertThat(report.isSafe()).isFalse();
    }

    @Test
    void inputHas4SafeReportsWithProblemDampener() {
        final Ex02 ex02 = new Ex02();

        assertThat(ex02.countSafeReportsWithProblemDampener("ex02.input.txt")).isEqualTo(4);
    }

    @Test
    void firstLineIsSafeWithProblemDampener() {
        final Ex02.Report report = Ex02.Report.createReport("7 6 4 2 1");
        assertThat(report.isSafeWithProblemDampener()).isTrue();
    }

    @Test
    void secondLineIsNotSafeWithProblemDampener() {
        final Ex02.Report report = Ex02.Report.createReport("1 2 7 8 9");
        assertThat(report.isSafeWithProblemDampener()).isFalse();
    }

    @Test
    void thirdLineIsNotSafeWithProblemDampener() {
        final Ex02.Report report = Ex02.Report.createReport("9 7 6 2 1");
        assertThat(report.isSafeWithProblemDampener()).isFalse();
    }

    @Test
    void fourthLineIsSafeWithProblemDampener() {
        final Ex02.Report report = Ex02.Report.createReport("1 3 2 4 5");
        assertThat(report.isSafeWithProblemDampener()).isTrue();
    }

    @Test
    void aLineSafeButLastIsSafe() {
        final Ex02.Report report = Ex02.Report.createReport("1 2 4 6 5");
        assertThat(report.isSafeWithProblemDampener()).isTrue();
    }

    @Test
    void firstFailingFromRealInputShouldBeSafe() {
        final Ex02.Report report = Ex02.Report.createReport("77 80 81 82 86");
        assertThat(report.isSafeWithProblemDampener()).isTrue();
    }

}
