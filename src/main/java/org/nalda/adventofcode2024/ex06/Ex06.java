package org.nalda.adventofcode2024.ex06;

import java.util.*;
import java.util.concurrent.*;

import static org.nalda.adventofcode2024Ø.ResourceUtil.getLineList;

public class Ex06 {

    public static final char COVERED = 'X';
    public static final char GUARD_NORTH = '^';
    public static final char OBSTACLE = '#';
    private final char[][] map;
    private final int height;
    private final int width;

    public static void main(String[] args) {
        Ex06 ex06 = new Ex06("ex06.input.txt");
        long guards = ex06.countGuardPositions();
        System.out.println(guards);

        ex06 = new Ex06("ex06.input.txt");
        final long obstructions = ex06.countObstructions();
        System.out.println(obstructions);
    }

    public Ex06(String inputName) {
        final List<String> lineList = getLineList(inputName);
        height = lineList.size();
        width = lineList.get(0).length();
        map = new char[height][width];
        for (int row = 0; row < lineList.size(); row++) {
            final String line = lineList.get(row);
            for (int col = 0; col < line.length(); col++) {
                map[row][col] = line.charAt(col);
            }
        }
    }

    public long countGuardPositions() {
        Position currentPosition = findGuard();
        coverMap(currentPosition);
        return countCoveredPositions();
    }

    private long countCoveredPositions() {
        long coveredPositions = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == COVERED) {
                    coveredPositions++;
                }
            }
        }
        return coveredPositions;
    }

    private void coverMap(Position currentPosition) {
        Direction currentDirection = Direction.N;

        currentPosition.setCovered();
        while (true) {
            Position nextPosition = currentPosition.move(currentDirection);
            if (nextPosition == null) {
                return;
            }

            while (nextPosition.isObstacle()) {
                currentDirection = currentDirection.turnRight();
                nextPosition = currentPosition.move(currentDirection);
                if (nextPosition == null) {
                    return;
                }
            }

            currentPosition = nextPosition;
            currentPosition.setCovered();
        }
    }

    private Position findGuard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == GUARD_NORTH) {
                    return new Position(row, col);
                }
            }
        }

        throw new RuntimeException("Guard not found");
    }

    public long countObstructions() {
        Position guardStartPosition = findGuard();
        coverMap(guardStartPosition);

        List<Position> obstacles = collectCandidateObstacles(guardStartPosition);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
        obstacles.stream()
                .map(obstacle -> (Callable<Boolean>) () -> findLoopWithObstacle(guardStartPosition, obstacle))
                .forEach(completionService::submit);

        long detectedLoops = 0L;

        for (int i = 0; i < obstacles.size(); i++) {
            final Future<Boolean> resultFuture;
            try {
                resultFuture = completionService.take();
            } catch (InterruptedException e) {
                throw new RuntimeException("Could not take!", e);
            }
            try {
                if (resultFuture.get()) {
                    detectedLoops++;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();

        return detectedLoops;
    }

    private List<Position> collectCandidateObstacles(Position guardStartPosition) {
        List<Position> obstacles = new ArrayList<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == COVERED) {
                    final Position candidatePos = new Position(row, col);
                    if (candidatePos.equals(guardStartPosition)) {
                        continue;
                    }

                    obstacles.add(candidatePos);
                }
            }
        }
        return obstacles;
    }

    private record Turn(Position p, Direction d) {
    }

    private boolean findLoopWithObstacle(Position guardStartPosition, Position obstacle) {
        Position currentPosition = guardStartPosition;
        Direction currentDirection = Direction.N;

        Set<Turn> turns = new HashSet<>();

        while (true) {
            Position nextPosition = currentPosition.move(currentDirection);
            if (nextPosition == null) {
                return false;
            }

            while (nextPosition.isObstacle() || nextPosition.equals(obstacle)) {
                currentDirection = currentDirection.turnRight();
                final Turn turn = new Turn(currentPosition, currentDirection);
                if (turns.contains(turn)) {
                    return true;
                }
                turns.add(turn);

                nextPosition = currentPosition.move(currentDirection);
                if (nextPosition == null) {
                    return false;
                }
            }

            currentPosition = nextPosition;
        }
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

        public void setCovered() {
            map[row][col] = COVERED;
        }

        public boolean isObstacle() {
            return map[row][col] == OBSTACLE;
        }

        @Override
        public String toString() {
            return "<" + row +
                    "," + col +
                    '>';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position p)) return false;
            return row == p.row && col == p.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private enum Direction {
        N (-1, 0),
        E (0, 1),
        S (1, 0),
        W (0, -1);

        private final int deltaRow;

        private final int deltaCol;

        Direction(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }

        public Direction turnRight() {
            return switch (this) {
                case N -> E;
                case E -> S;
                case S -> W;
                case W -> N;
            };
        }
    }


}
