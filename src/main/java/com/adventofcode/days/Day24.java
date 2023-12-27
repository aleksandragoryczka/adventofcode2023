package com.adventofcode.days;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Vector;

import com.adventofcode.utils.ReaderUtil;

public class Day24 {
    private static class RaysPair {
        static Vector<BigDecimal> p1, v1, p2, v2;
        static BigDecimal a1, a2;

        public RaysPair(long p1x, long p1y, long v1x, long v1y, long p2x, long p2y, long v2x, long v2y) {
            p1 = new Vector<>(2);
            p1.add(BigDecimal.valueOf(p1x));
            p1.add(BigDecimal.valueOf(p1y));
            v1 = new Vector<>(2);
            v1.add(BigDecimal.valueOf(v1x));
            v1.add(BigDecimal.valueOf(v1y));
            p2 = new Vector<>(2);
            p2.add(BigDecimal.valueOf(p2x));
            p2.add(BigDecimal.valueOf(p2y));
            v2 = new Vector<>(2);
            v2.add(BigDecimal.valueOf(v2x));
            v2.add(BigDecimal.valueOf(v2y));
        }

        public boolean countFactors() {
            Vector<BigDecimal> diff = new Vector<>(2);
            diff.add(p2.get(0).subtract(p1.get(0)));
            diff.add(p2.get(1).subtract(p1.get(1)));

            BigDecimal denominator = (v2.get(0).multiply(v1.get(1)).subtract(v2.get(1).multiply(v1.get(0))));
            if (denominator.compareTo(BigDecimal.ZERO) != 0) {
                a1 = (diff.get(1).multiply(v2.get(0)).subtract(diff.get(0).multiply(v2.get(1)))).divide(denominator,
                        MathContext.DECIMAL128);
                a2 = (diff.get(1).multiply(v1.get(0)).subtract(diff.get(0).multiply(v1.get(1)))).divide(denominator,
                        MathContext.DECIMAL128);
                if (a1.compareTo(BigDecimal.ZERO) == 1 && a2.compareTo(BigDecimal.ZERO) == 1) {
                    return true;
                }
            }
            return false;
        }

        public boolean checkIntersectionPoint() {
            BigDecimal intersectionX = p1.get(0).add(a1.multiply(v1.get(0)));
            BigDecimal intersectionY = p1.get(1).add(a1.multiply(v1.get(1)));
            if (intersectionX.compareTo(new BigDecimal("400000000000000")) <= 0
                    && intersectionY.compareTo(new BigDecimal("400000000000000")) <= 0
                    && intersectionX.compareTo(new BigDecimal("200000000000000")) >= 0
                    && intersectionY.compareTo(new BigDecimal("200000000000000")) >= 0) {
                return true;
            }
            return false;
        }
    }

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        Long sum = 0L;
        for (int i = 0; i < lines.size() - 1; i++) {
            String[] lineSplitted = lines.get(i).split("[,@]");
            for (int j = i + 1; j < lines.size(); j++) {
                String[] lineSplitted2 = lines.get(j).split("[,@]");
                RaysPair pair = new RaysPair(Long.parseLong(lineSplitted[0].trim()),
                        Long.parseLong(lineSplitted[1].trim()),
                        Long.parseLong(lineSplitted[3].trim()), Long.parseLong(lineSplitted[4].trim()),
                        Long.parseLong(lineSplitted2[0].trim()), Long.parseLong(lineSplitted2[1].trim()),
                        Long.parseLong(lineSplitted2[3].trim()),
                        Long.parseLong(lineSplitted2[4].trim()));
                if (pair.countFactors()) {
                    if (pair.checkIntersectionPoint()) {
                        sum++;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    public static void task2(String filePath) {
    }
}
