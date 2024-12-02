package org.nalda.adventofcode2024.ex02;

import java.util.Arrays;

import static org.nalda.adventofcode2024Ø.ResourceUtil.*;

public class Ex02 {
    public long countSafeReports(String inputName) {
        return getLineStream(inputName)
                .map(Report::new)
                .filter(Report::isSafe)
                .count();
    }

    public static void main(String[] args) {
        final Ex02 ex02 = new Ex02();
        System.out.println(ex02.countSafeReports("ex02.input.txt"));
    }

    static class Report {

        private final int[] levels;

        public Report(String input) {
            levels = Arrays.stream(input.split("\s+")).mapToInt(Integer::parseInt).toArray();
            if (levels.length < 2) {
                throw new IllegalArgumentException("Report must have at least 2 levels");
            }
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

                if (diff <= 0) {
                    return false;
                }

                if (diff > 3) {
                    return false;
                }
            }

            return true;
        }
    }
}
