package org.nalda.adventofcode2024.ex03;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex03Test {
    @Test
    void testSampleInput() {
        final Ex03 ex03 = new Ex03();

        final BigInteger actual = ex03.sumMultiplications("ex03.input.txt");

        assertThat(actual).isEqualTo(BigInteger.valueOf(161));
    }

    @Test
    void testSampleInputWithEnablement() {
        final Ex03 ex03 = new Ex03();

        final BigInteger actual = ex03.sumMultiplicationsWithEnablement("ex03.input.txt");

        assertThat(actual).isEqualTo(BigInteger.valueOf(48));
    }
}
