package org.nalda.adventofcode2024.ex06;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex06Test {

    @Test
    void countGuardPositions() {
        Ex06 ex06 = new Ex06("ex06.input.txt");
        long guards = ex06.countGuardPositions();

        assertThat(guards).isEqualTo(41);
    }

    @Test
    void countObstructions() {
        Ex06 ex06 = new Ex06("ex06.input.txt");
        long obstructions = ex06.countObstructions();

        assertThat(obstructions).isEqualTo(6);
    }
}