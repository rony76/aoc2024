package org.nalda.adventofcode2024.ex07;

import java.math.BigInteger;
import java.util.Arrays;

import static org.nalda.adventofcode2024Ø.ResourceUtil.*;

public class Ex07 {

    public static void main(String[] args) {
        Ex07 ex07 = new Ex07("ex07.input.txt");
        BigInteger sum = ex07.sumOfCalibrations();
        System.out.println(sum);

        sum = ex07.sumOfCalibrationsWithConcat();
        System.out.println(sum);
    }

    private final String inputName;

    public Ex07(String inputName) {
        this.inputName = inputName;
    }

    public BigInteger sumOfCalibrations() {
        return getLineStream(inputName)
                .filter(line -> !line.isEmpty())
                .map(Calibration::new)
                .filter(Calibration::canBeMadeTrue)
                .map(Calibration::getTestValue)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public BigInteger sumOfCalibrationsWithConcat() {
        return getLineStream(inputName)
                .filter(line -> !line.isEmpty())
                .map(Calibration::new)
                .filter(Calibration::canBeMadeTrueWithConcat)
                .map(Calibration::getTestValue)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private class Calibration {

        private final BigInteger testValue;
        private final BigInteger[] terms;

        public Calibration(String line) {
            final String[] testValueAndTerms = line.split(":");
            testValue = new BigInteger(testValueAndTerms[0]);
            terms = Arrays.stream(testValueAndTerms[1].split(" "))
                    .map(String::trim)
                    .filter(term -> !term.isEmpty())
                    .map(BigInteger::new)
                    .toArray(BigInteger[]::new);
        }

        public boolean canBeMadeTrue() {
            return yieldsTestValue(terms[0], 1);
        }

        public boolean canBeMadeTrueWithConcat() {
            return yieldsTestValueWithConcat(terms[0], 1);
        }

        private boolean yieldsTestValue(BigInteger acc, int index) {
            if (index == terms.length) {
                return acc.equals(testValue);
            }

            return yieldsTestValue(acc.add(terms[index]), index + 1) ||
                    yieldsTestValue(acc.multiply(terms[index]), index + 1);
        }

        private boolean yieldsTestValueWithConcat(BigInteger acc, int index) {
            if (index == terms.length) {
                return acc.equals(testValue);
            }

            return yieldsTestValueWithConcat(acc.add(terms[index]), index + 1) ||
                    yieldsTestValueWithConcat(acc.multiply(terms[index]), index + 1) ||
                    yieldsTestValueWithConcat(new BigInteger(acc + terms[index].toString()), index + 1);
        }

        public BigInteger getTestValue() {
            return testValue;
        }
    }
}
