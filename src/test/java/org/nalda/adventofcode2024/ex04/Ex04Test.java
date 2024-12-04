package org.nalda.adventofcode2024.ex04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex04Test {
    @Test
    void findXmasInInput() {
        Ex04 ex04 = new Ex04("ex04.input.txt");
        long xmases = ex04.countXmas();

        assertThat(xmases).isEqualTo(18);
    }

    @Test
    void findXmasInExample() {
        Ex04 ex04 = new Ex04("ex04.input.txt");
        long xmases = ex04.countMasCross();

        assertThat(xmases).isEqualTo(9);
    }
}
