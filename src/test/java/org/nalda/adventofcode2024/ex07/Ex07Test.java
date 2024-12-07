package org.nalda.adventofcode2024.ex07;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex07Test {
    @Test
    void sumOfCalibrations() {
        Ex07 ex07 = new Ex07("ex07.input.txt");
        BigInteger sum = ex07.sumOfCalibrations();

        assertThat(sum).isEqualTo(new BigInteger("3749"));
    }

    @Test
    void sumOfCalibrationsWithConcat() {
        Ex07 ex07 = new Ex07("ex07.input.txt");
        BigInteger sum = ex07.sumOfCalibrationsWithConcat();

        assertThat(sum).isEqualTo(new BigInteger("11387"));
    }
}