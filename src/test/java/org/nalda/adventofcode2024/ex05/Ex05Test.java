package org.nalda.adventofcode2024.ex05;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex05Test {
    @Test
    void countCorrectUpdates() {
        Ex05 ex05 = new Ex05("ex05.input.txt");
        long updates = ex05.countCorrectUpdates();

        assertThat(updates).isEqualTo(143);
    }
}
