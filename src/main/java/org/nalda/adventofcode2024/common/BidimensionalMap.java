package org.nalda.adventofcode2024.common;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.nalda.adventofcode2024.ResourceUtil.getLineList;

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

    public <T> T findFirstPosition(Function<Position, T> function) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                final T result = function.apply(positionAt(row, col));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public <T> T reduce(BiFunction<T, Position, T> accumulator, T initial) {
        T result = initial;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result = accumulator.apply(result, positionAt(row, col));
            }
        }
        return result;
    }

    public void forEach(Consumer<Position> consumer) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                consumer.accept(positionAt(row, col));
            }
        }
    }

    public class Position {

        protected final int row;
        protected final int col;

        protected Position(int row, int col) {
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
            return positionAt(row, col);
        }

        public Position move(Distance distance) {
            final int row = this.row + distance.deltaRow;
            if (row < 0 || row >= height) {
                return null;
            }
            final int col = this.col + distance.deltaCol;
            if (col < 0 || col >= width) {
                return null;
            }
            return positionAt(row, col);
        }

        public char getChar() {
            return data[row][col];
        }

        public void setChar(char c) {
            data[row][col] = c;
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


        public Distance getDistanceFrom(Position other) {
            return new Distance(other.row - row, other.col - col);
        }
    }

    public class Distance {
        private final int deltaRow;
        private final int deltaCol;

        public Distance(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }

        public Distance invert() {
            return new Distance(-deltaRow, -deltaCol);
        }

        @Override
        public String toString() {
            return "<" + deltaRow +
                    "," + deltaCol +
                    '>';
        }
    }

    public enum Direction  {
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
