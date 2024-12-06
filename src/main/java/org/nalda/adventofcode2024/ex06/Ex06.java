package org.nalda.adventofcode2024.ex06;

import org.nalda.adventofcode2024.common.BidimensionalMap;
import org.nalda.adventofcode2024.common.BidimensionalMap.Direction;
import org.nalda.adventofcode2024.common.BidimensionalMap.Position;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public class Ex06 {

    public static final char COVERED = 'X';
    public static final char GUARD_NORTH = '^';
    public static final char OBSTACLE = '#';
    private final BidimensionalMap map;

    public static void main(String[] args) {
        Ex06 ex06 = new Ex06("ex06.input.txt");
        long guards = ex06.countGuardPositions();
        System.out.println(guards);

        ex06 = new Ex06("ex06.input.txt");
        final long obstructions = ex06.countObstructions();
        System.out.println(obstructions);
    }

    public Ex06(String inputName) {
        map = new BidimensionalMap(inputName);
    }

    public long countGuardPositions() {
        Position currentPosition = findGuard();
        coverMap(currentPosition);
        return countCoveredPositions();
    }

    private long countCoveredPositions() {
        return map.reduce((coveredPositions, position) -> {
            if (position.getChar() == COVERED) {
                return coveredPositions + 1;
            }
            return coveredPositions;
        }, 0L);
    }

    private void coverMap(Position currentPosition) {
        Direction currentDirection = Direction.N;

        currentPosition.setChar(COVERED);
        while (true) {
            Position nextPosition = currentPosition.move(currentDirection);
            if (nextPosition == null) {
                return;
            }

            while (nextPosition.getChar() == OBSTACLE) {
                currentDirection = currentDirection.turnRight90();
                nextPosition = currentPosition.move(currentDirection);
                if (nextPosition == null) {
                    return;
                }
            }

            currentPosition = nextPosition;
            currentPosition.setChar(COVERED);
        }
    }

    private Position findGuard() {
        final Function<Position, Position> guardFinder = position -> position.getChar() == GUARD_NORTH ? position : null;
        Position guardPosition = map.findFirstPosition(guardFinder);

        if (guardPosition != null) {
            return guardPosition;
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
        return map.reduce((obstacles, position) -> {
            if ((position.getChar() == COVERED) && !position.equals(guardStartPosition)) {
                obstacles.add(position);
            }
            return obstacles;
        }, new ArrayList<>());
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

            while (nextPosition.getChar() == OBSTACLE || nextPosition.equals(obstacle)) {
                currentDirection = currentDirection.turnRight90();
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

}
