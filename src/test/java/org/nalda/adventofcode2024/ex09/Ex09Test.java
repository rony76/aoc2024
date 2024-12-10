package org.nalda.adventofcode2024.ex09;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class Ex09Test {
    @Test
    void testChecksumForTestInput() {
        Ex09 ex09 = new Ex09("2333133121414131402");

        BigInteger result = ex09.checksum();

        assertThat(result).isEqualTo(new BigInteger("1928"));
    }

    @Test
    void testChecksumForSimplerInput() {
        Ex09 ex09 = new Ex09("12345");

        BigInteger result = ex09.checksum();

        assertThat(result).isEqualTo(new BigInteger("60"));
    }

    @Test
    void testChecksumForSimplestInput() {
        Ex09 ex09 = new Ex09("1");

        BigInteger result = ex09.checksum();

        assertThat(result).isEqualTo(new BigInteger("0"));
    }

    @Test
    void testChecksumForOneSpace() {
        Ex09 ex09 = new Ex09("111");

        BigInteger result = ex09.checksum();

        assertThat(result).isEqualTo(new BigInteger("1"));
    }
}