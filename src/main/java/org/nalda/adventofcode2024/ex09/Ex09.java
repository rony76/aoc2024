package org.nalda.adventofcode2024.ex09;

import java.math.BigInteger;
import java.util.Objects;

import static org.nalda.adventofcode2024.ResourceUtil.*;

public class Ex09 {

    private final String inputLine;

    public static void main(String[] args) {
        Ex09 ex09 = Ex09.forResource("ex09.input.txt");

        BigInteger result = ex09.checksum();

        System.out.println(result);
    }

    public static Ex09 forResource(String resourceName) {
        String inputLine = getLineList(resourceName).get(0);
        return new Ex09(inputLine);
    }

    public Ex09(String inputLine) {
        this.inputLine = inputLine;
    }

    private class Block {
        final int pos;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return pos == block.pos;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(pos);
        }

        private Block(int pos) {
            this.pos = pos;
        }

        boolean isSpace() {
            return pos % 2 == 1;
        }

        int getLength() {
            return inputLine.charAt(pos) - '0';
        }

        int getId() {
            return pos / 2;
        }

        public boolean before(Block other) {
            return pos < other.pos;
        }

        public Block prev() {
            int newPos = pos - 1;
            if (newPos < 0) {
                throw new RuntimeException("Going down... went too far");
            }
            return new Block(newPos);
        }

        public Block next() {
            int newPos = pos + 1;
            if (newPos >= inputLine.length()) {
                throw new RuntimeException("Going up... went too far");
            }
            return new Block(newPos);
        }

        @Override
        public String toString() {
            return getLength() + "@" + pos + '(' + getId() + ')';
        }
    }

    public BigInteger checksum() {
        Block counting = new Block(0);
        Block picking = new Block(inputLine.length() - 1);

        BigInteger result = BigInteger.ZERO;
        int index = 0;

        int leftOverCount = 0;

        while (counting.before(picking)) {
            if (!counting.isSpace()) {
                for (int i = 0; i < counting.getLength(); i++) {
                    System.out.printf("Adding at position %d, ID %d%n", index, counting.getId());
                    result = result.add(BigInteger.valueOf((long) index++ * counting.getId()));
                }
            } else {
                int spaces = counting.getLength();
                if (leftOverCount > 0) {
                    int valuesFromLeftOver = Math.min(leftOverCount, spaces);
                    for (int i = 0; i < valuesFromLeftOver; i++) {
                        System.out.printf("Adding at position %d, ID %d%n", index, picking.getId());
                        result = result.add(BigInteger.valueOf((long) index++ * picking.getId()));
                    }
                    if (spaces >= leftOverCount) {
                        picking = picking.prev();
                    }
                    leftOverCount -= valuesFromLeftOver;
                    spaces -= valuesFromLeftOver;
                }

                while (spaces > 0) {
                    while (picking.isSpace()) {
                        picking = picking.prev();
                    }
                    if (picking.before(counting)) {
                        break;
                    }
                    int pickingLength = picking.getLength();
                    int copyCountFromPicking = Math.min(spaces, pickingLength);
                    for (int i = 0; i < copyCountFromPicking; i++) {
                        System.out.printf("Adding at position %d, ID %d%n", index, picking.getId());
                        result = result.add(BigInteger.valueOf((long) index++ * picking.getId()));
                    }
                    if (pickingLength > spaces) {
                        leftOverCount = pickingLength - spaces;
                    } else {
                        picking = picking.prev();
                    }

                    spaces -= copyCountFromPicking;
                }
            }

            counting = counting.next();
        }

        if (counting.equals(picking)) {
            for (int i = 0; i < leftOverCount; i++) {
                System.out.printf("Adding at position %d, ID %d%n", index, picking.getId());
                result = result.add(BigInteger.valueOf((long) index++ * picking.getId()));
            }

        }

        return result;
    }

}
