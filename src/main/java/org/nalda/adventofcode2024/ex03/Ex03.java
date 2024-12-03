package org.nalda.adventofcode2024.ex03;

import java.math.BigInteger;
import java.util.regex.Pattern;

import static org.nalda.adventofcode2024Ø.ResourceUtil.*;

public class Ex03 {
    public static void main(String[] args) {
        final Ex03 ex03 = new Ex03();

        final BigInteger actual = ex03.sumMultiplications("ex03.input.txt");

        System.out.println(actual);
    }

    private static final Pattern PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public BigInteger sumMultiplications(String inputName) {
        return getLineStream(inputName)
                .map(this::sumLine)
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO);
    }

    private BigInteger sumLine(String line) {
        var matcher = PATTERN.matcher(line);
        var result = BigInteger.ZERO;

        while (matcher.find()) {
            var a = new BigInteger(matcher.group(1));
            var b = new BigInteger(matcher.group(2));
            result = result.add(a.multiply(b));
        }
        return result;
    }
}
