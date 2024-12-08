package org.nalda.adventofcode2024.ex08;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex08Test {
    @Test
    void countAntinodes() {
        Ex08 ex08 = new Ex08("ex08.input.txt");
        long count = ex08.countAntinodes();

        assertThat(count).isEqualTo(14);
    }

    @Test
    void countAntinodesWithHarmonics() {
        Ex08 ex08 = new Ex08("ex08.input.txt");
        long count = ex08.countAntinodesWithHarmonics();

        assertThat(count).isEqualTo(34);
    }
}