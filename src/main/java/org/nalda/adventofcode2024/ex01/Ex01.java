package org.nalda.adventofcode2024.ex01;

import java.util.ArrayList;
import java.util.HashMap;

import static org.nalda.adventofcode2024.ResourceUtil.getLineStream;

public class Ex01 {
    public static void main(String[] args) {
        totalDistance();
        totalSimilarityScore();
    }

    private static void totalDistance() {
        var list1 = new ArrayList<Integer>();
        var list2 = new ArrayList<Integer>();
        getLineStream("ex01.input.txt")
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    final String[] items = line.split("\\s+");
                    list1.add(Integer.parseInt(items[0]));
                    list2.add(Integer.parseInt(items[1]));
                });

        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        long totalDistance = 0L;

        for (int i = 0; i < list1.size(); i++) {
            var distance = Math.abs(list1.get(i) - list2.get(i));
            totalDistance += distance;
        }

        System.out.println(totalDistance);
    }

    private static void totalSimilarityScore() {
        var list = new ArrayList<Integer>();
        var occurrences = new HashMap<Integer, Integer>();
        getLineStream("ex01.input.txt")
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    final String[] items = line.split("\\s+");
                    list.add(Integer.parseInt(items[0]));
                    occurrences.compute(Integer.parseInt(items[1]), (k, v) -> v == null ? 1 : v + 1);
                });

        long similarityScore = 0L;

        for (Integer i : list) {
            similarityScore += (long) i * occurrences.getOrDefault(i, 0);
        }

        System.out.println(similarityScore);
    }
}
