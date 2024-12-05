package org.nalda.adventofcode2024.ex05;

import java.util.*;

import static org.nalda.adventofcode2024Ø.ResourceUtil.*;

public class Ex05 {

    private final Map<Integer, Set<Integer>> rules;
    private final List<Update> updates;

    private class Update {
        private final int[] values;

        private Update(int[] values) {
            this.values = values;
        }

        public int middlePage() {
            return values[values.length / 2];
        }

        public boolean isCorrect() {
            for (int i = 1; i < values.length; i++) {
                final int value = values[i];
                final Set<Integer> followers = rules.get(value);
                if (followers == null) {
                    continue;
                }
                for (int j = 0; j < i; j++) {
                    if (followers.contains(values[j])) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public Ex05(String inputName) {
        final List<String> lines = getLineList(inputName);
        final int separator = lines.indexOf("");

        rules = new TreeMap<>();
        lines.subList(0, separator).forEach(this::parseRule);

        updates = new ArrayList<>();
        lines.subList(separator + 1, lines.size()).forEach(this::parseUpdate);
    }

    public static void main(String[] args) {
        final Ex05 ex05 = new Ex05("ex05.input.txt");
        final long correctUpdates = ex05.countCorrectUpdates();
        System.out.println(correctUpdates);
    }

    private void parseUpdate(String updateLine) {
        final String[] values = updateLine.split(",");
        final int[] intValues = Arrays.stream(values)
                .mapToInt(Integer::parseInt)
                .toArray();
        updates.add(new Update(intValues));
    }

    private void parseRule(String ruleLine) {
        final String[] terms = ruleLine.trim().split("\\|");
        final int key = Integer.parseInt(terms[0]);
        final int dependency = Integer.parseInt(terms[1]);
        rules.computeIfAbsent(key, HashSet::new).add(dependency);
    }

    public long countCorrectUpdates() {
        return updates.stream()
                .filter(Update::isCorrect)
                .mapToLong(Update::middlePage)
                .sum();
    }
}
