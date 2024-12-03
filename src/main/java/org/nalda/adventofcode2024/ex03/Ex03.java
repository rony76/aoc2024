package org.nalda.adventofcode2024.ex03;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.nalda.adventofcode2024Ø.ResourceUtil.*;

public class Ex03 {
    private boolean enabled = true;

    public static void main(String[] args) {
        final Ex03 ex03 = new Ex03();

        System.out.println(ex03.sumMultiplications("ex03.input.txt"));
        System.out.println(ex03.sumMultiplicationsWithEnablement("ex03.input.txt"));
    }

    private static final String MUL_REGEXP = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    private static final String DO_REGEXP = "do\\(\\)";
    private static final String DONT_REGEXP = "don't\\(\\)";

    private static final Pattern MUL_PATTERN = Pattern.compile(MUL_REGEXP);
    private static final Pattern MUL_ENABLE_PATTERN = Pattern.compile("(" + MUL_REGEXP + ")|("+ DO_REGEXP + ")|(" + DONT_REGEXP + ")");

    public BigInteger sumMultiplications(String inputName) {
        return getLineStream(inputName)
                .map(this::sumLine)
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO);
    }

    private BigInteger sumLine(String line) {
        var matcher = MUL_PATTERN.matcher(line);
        var result = BigInteger.ZERO;

        while (matcher.find()) {
            var a = new BigInteger(matcher.group(1));
            var b = new BigInteger(matcher.group(2));
            result = result.add(a.multiply(b));
        }
        return result;
    }

    public BigInteger sumMultiplicationsWithEnablement(String inputName) {
        this.enabled = true;
        return getLineStream(inputName)
                .map(this::sumLineWithEnablement)
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO);
    }

    private BigInteger sumLineWithEnablement(String line) {
        final Matcher matcher = MUL_ENABLE_PATTERN.matcher(line);
        var result = BigInteger.ZERO;

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                if (enabled) {
                    var a = new BigInteger(matcher.group(2));
                    var b = new BigInteger(matcher.group(3));
                    result = result.add(a.multiply(b));
                }
            } else if (matcher.group(4) != null) {
                enabled = true;
            } else if (matcher.group(5) != null) {
                enabled = false;
            }
        }

        return result;
    }
}
