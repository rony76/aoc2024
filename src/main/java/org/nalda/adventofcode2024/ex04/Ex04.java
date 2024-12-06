package org.nalda.adventofcode2024.ex04;

import org.nalda.adventofcode2024.common.BidimensionalMap;
import org.nalda.adventofcode2024.common.BidimensionalMap.Direction;
import org.nalda.adventofcode2024.common.BidimensionalMap.Position;

import java.util.Arrays;

public class Ex04 {

    private static final char[] XMAS_CHARS = "XMAS".toCharArray();
    private final BidimensionalMap map;

    public static void main(String[] args) {
        Ex04 ex04 = new Ex04("ex04.input.txt");
        long xmases = ex04.countXmas();
        System.out.println(xmases);
        xmases = ex04.countMasCross();
        System.out.println(xmases);
    }

    public Ex04(String inputName) {
        map = new BidimensionalMap(inputName);
    }

    public long countXmas() {
        long result = 0;

        for (int row = 0; row < map.height; row++) {
            for (int col = 0; col < map.width; col++) {
                if (map.charAt(row, col) == 'X') {
                    result += countXmasFrom(row, col);
                }
            }
        }

        return result;
    }

    public long countMasCross() {
        long result = 0;

        for (int row = 0; row < map.height; row++) {
            for (int col = 0; col < map.width; col++) {
                if (map.charAt(row, col) == 'A') {
                    result += countMasAround(row, col);
                }
            }
        }

        return result;
    }

    private long countXmasFrom(int row, int col) {
        final Position position = map.positionAt(row, col);

        return Arrays.stream(Direction.values())
                .filter(dir -> findXmasFromPosMovingInDirection(position, dir))
                .count();
    }

    private boolean findXmasFromPosMovingInDirection(Position pos, Direction dir) {
        for (int i = 0; i < XMAS_CHARS.length; i++, pos = pos.move(dir)) {
            if (pos == null || (pos.getChar() != XMAS_CHARS[i])) {
                return false;
            }
        }
        return true;
    }

    private long countMasAround(int row, int col) {
        final Position position = map.positionAt(row, col);

        long result = 0;
        if (findMasCross(position, Direction.NE)) {
            result++;
        }

        return result;
    }

    private boolean findMasCross(Position position, Direction dir) {
        return findMas(position, dir) && findMas(position, dir.turnRight90());
    }

    private boolean findMas(Position position, Direction dir) {
        final Position p1 = position.move(dir);
        if (p1 == null) {
            return false;
        }
        final Position p2 = position.move(dir.opposite());
        if (p2 == null) {
            return false;
        }
        final char c1 = p1.getChar();
        final char c2 = p2.getChar();

        return (c1 == 'M' && c2 == 'S') || (c1 == 'S' && c2 == 'M');
    }
}
