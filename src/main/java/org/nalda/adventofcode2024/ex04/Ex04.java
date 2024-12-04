package org.nalda.adventofcode2024.ex04;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.nalda.adventofcode2024Ø.ResourceUtil.getLineList;

public class Ex04 {

    private static final char[] XMAS_CHARS = "XMAS".toCharArray();

    public static void main(String[] args) {
        Ex04 ex04 = new Ex04("ex04.input.txt");
        long xmases = ex04.countXmas();
        System.out.println(xmases);
    }

    private final class Position {
        private final int row;
        private final int col;

        private Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Position move(Direction direction) {
            final int row = this.row + direction.deltaRow;
            if (row < 0 || row >= height) {
                return null;
            }
            final int col = this.col + direction.deltaCol;
            if (col < 0 || col >= width) {
                return null;
            }
            return new Position(row, col);
        }

        public char getChar() {
            return data[row][col];
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Position) obj;
            return this.row == that.row &&
                    this.col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "<" + row + ", " + col + '>';
        }


    }

    private enum Direction {
        N (-1, 0),
        NE (-1, 1),
        E (0, 1),
        SE (1, 1),
        S (1, 0),
        SW (1, -1),
        W (0, -1),
        NW(-1, -1);

        private final int deltaRow;
        private final int deltaCol;

        Direction(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }
    }

    private final char[][] data;
    private final int height;
    private final int width;

    public Ex04(String inputName) {
        final List<String> lines = getLineList(inputName);

        height = lines.size();
        width = lines.get(0).length();
        data = new char[height][];

        for (int i = 0; i < lines.size(); i++) {
            data[i] = new char[width];
            lines.get(i).getChars(0, width, data[i], 0);
        }
    }

    public long countXmas() {
        long result = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (data[row][col] == 'X') {
                    result += countXmasFrom(row, col);
                }
            }
        }

        return result;
    }

    private long countXmasFrom(int row, int col) {
        final Position position = new Position(row, col);

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
}
