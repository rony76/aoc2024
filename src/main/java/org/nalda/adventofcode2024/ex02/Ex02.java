package org.nalda.adventofcode2024.ex02;

import java.util.Arrays;

import static org.nalda.adventofcode2024.ResourceUtil.*;

public class Ex02 {
    public long countSafeReports(String inputName) {
        return getLineStream(inputName)
                .map(Report::createReport)
                .filter(Report::isSafe)
                .count();
    }

    public long countSafeReportsWithProblemDampener(String inputName) {
        return getLineStream(inputName)
                .map(Report::createReport)
                .filter(Report::isSafeWithProblemDampener)
                .count();
    }

    public static void main(String[] args) {
        final Ex02 ex02 = new Ex02();
        System.out.println(ex02.countSafeReports("ex02.input.txt"));
        System.out.println(ex02.countSafeReportsWithProblemDampener("ex02.input.txt"));
    }

    static class Report {
        private final int[] levels;

        private Report(int[] levels) {
            this.levels = levels;
        }

        public static Report createReport(String input) {
            int[] levels = Arrays.stream(input.split("\s+")).mapToInt(Integer::parseInt).toArray();
            if (levels.length < 2) {
                throw new IllegalArgumentException("Report must have at least 2 levels");
            }
            return new Report(levels);
        }

        public boolean isSafe() {
            if (levels[0] == levels[1]) {
                return false;
            }

            boolean goingUp = levels[0] < levels[1];

            for (int i = 0; i < levels.length - 1; i++) {
                int diff = levels[i + 1] - levels[i];
                if (!goingUp) {
                    diff = -diff;
                }

                if (diff < 1) {
                    return false;
                }

                if (diff > 3) {
                    return false;
                }
            }

            return true;
        }

        public boolean isSafeWithProblemDampener() {
            if (isSafe()) {
                return true;
            }
            for (int i = 0; i < levels.length; i++) {
                if (subReportBut(i).isSafe()) {
                    return true;
                }
            }
            return false;
        }

        private Report subReportBut(int index) {
            int[] subLevels = new int[levels.length - 1];
            for (int i = 0; i < levels.length - 1; i++) {
                subLevels[i] = i < index ? levels[i] : levels[i + 1];
            }

            return new Report(subLevels);
        }

        @Override
        public String toString() {
            return "Report{" + Arrays.toString(levels) + '}';
        }
    }
}
