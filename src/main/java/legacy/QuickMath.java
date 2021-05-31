package legacy;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuickMath {
    public static boolean matrixEquals(int n, final Util.Getter a, final Util.Getter b, final double epsilon) {
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                flag &= (isZero(Math.abs(a.get(i, j) - b.get(i, j)), epsilon));
                if (!flag) {
                    System.out.println("i = " + i + ", j = " + j + ", diff = " + Math.abs(a.get(i, j) - b.get(i, j)));
                }
            }
        }
        return flag;
    }

    private static final double EPSILON = 1E-18;

    public static boolean isZero(final double d, final double epsilon) {
        return Math.abs(d) < epsilon;
    }

    public static boolean isZero(final double d) {
        return isZero(d, EPSILON);
    }

    public static double[] subtract(final double[] a, final double[] b) {
        checkDimensions(a, b);
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] - b[i]).toArray();
    }

    public static double norm(final double[] a) {
        return Math.sqrt(scalar(a, a));
    }

    public static double scalar(final double[] a, final double[] b) {
        checkDimensions(a, b);
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] * b[i]).sum();
    }

    public static double[] add(final double[] a, final double[] b) {
        checkDimensions(a, b);
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] + b[i]).toArray();
    }

    public static double[] multiply(final double a, final double[] b) {
        return Arrays.stream(b).map(i -> a * i).toArray();
    }

    private static void checkDimensions(final double[] a, final double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException(String.format("a and b should be the same length: %d, %d", a.length, b.length));
        }
    }
}
