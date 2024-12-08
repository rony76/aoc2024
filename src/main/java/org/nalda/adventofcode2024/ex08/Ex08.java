package org.nalda.adventofcode2024.ex08;

import org.nalda.adventofcode2024.common.BidimensionalMap;
import org.nalda.adventofcode2024.common.BidimensionalMap.Distance;
import org.nalda.adventofcode2024.common.BidimensionalMap.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex08 {

    public static void main(String[] args) {
        Ex08 ex08 = new Ex08("ex08.input.txt");
        long count = ex08.countAntinodes();
        System.out.println(count);
    }

    private final BidimensionalMap map;

    public Ex08(String inputName) {
        map = new BidimensionalMap(inputName);
    }

    public long countAntinodes() {
        Map<Character, List<Position>> positionsByChar = buildPositionsByChar();

        findAntinodes(positionsByChar);

        return map.reduce((count, position) -> position.getChar() == '#' ? count + 1 : count, 0L);
    }

    private void findAntinodes(Map<Character, List<Position>> positionsByChar) {
        positionsByChar.forEach((c, positions) -> {
            if (positions.size() == 1) {
                return;
            }

            for (int i = 0; i < positions.size() - 1; i++) {
                final Position p1 = positions.get(i);
                for (int j = i + 1; j < positions.size(); j++) {
                    final Position p2 = positions.get(j);
                    final Distance distance = p1.getDistanceFrom(p2);

                    Position antinodeCandidate = p2.move(distance);
                    if (antinodeCandidate != null) {
                        antinodeCandidate.setChar('#');
                    }

                    antinodeCandidate = p1.move(distance.invert());
                    if (antinodeCandidate != null) {
                        antinodeCandidate.setChar('#');
                    }
                }
            }

        });
    }

    private Map<Character, List<Position>> buildPositionsByChar() {
        Map<Character, List<Position>> nodesByCharacter = new HashMap<>();
        map.forEach(p -> {
            if (p.getChar() == '.') {
                return;
            }

            nodesByCharacter.computeIfAbsent(p.getChar(), c -> new ArrayList<>()).add(p);
        });
        return nodesByCharacter;
    }
}
