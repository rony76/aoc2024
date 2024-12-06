package org.nalda.adventofcode2024.common;

import java.util.List;
import java.util.Objects;

import static org.nalda.adventofcode2024Ø.ResourceUtil.getLineList;

public class BidimensionalMap {
    public final char[][] data;

    public final int height;
    public final int width;

    public BidimensionalMap(String resourceName) {
        final List<String> lines = getLineList(resourceName);

        height = lines.size();
        width = lines.get(0).length();
        data = new char[height][];

        for (int i = 0; i < lines.size(); i++) {
            data[i] = new char[width];
            lines.get(i).getChars(0, width, data[i], 0);
        }
    }

    public char charAt(int row, int col) {
        return data[row][col];
    }

    public Position positionAt(int row, int col) {
        return new Position(row, col);
    }

    public class Position {

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
            if (!(obj instanceof Position p)) return false;
            return row == p.row && col == p.col;
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

    public enum Direction {
        N(-1, 0),
        NE(-1, 1),
        E(0, 1),
        SE(1, 1),
        S(1, 0),
        SW(1, -1),
        W(0, -1),
        NW(-1, -1);

        private final int deltaRow;

        private final int deltaCol;

        Direction(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }

        public Direction opposite() {
            return switch (this) {
                case N -> S;
                case NE -> SW;
                case E -> W;
                case SE -> NW;
                case S -> N;
                case SW -> NE;
                case W -> E;
                case NW -> SE;
            };
        }

        public Direction turnRight90() {
            return switch (this) {
                case N -> E;
                case NE -> SE;
                case E -> S;
                case SE -> SW;
                case S -> W;
                case SW -> NW;
                case W -> N;
                case NW -> NE;
            };
        }
    }

}
