package super_lego;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class KanyeEast {

    private static Algorithm unwrapAlgo(final String name, final OptimizationAlgorithm algo) {
        return new Algorithm((somethingStupid, epsilon) -> {
            final double left = somethingStupid.getLeft();
            final double right = somethingStupid.getRight();
            final NorthWest result = new NorthWest(name, left, right);
            final double x = algo.apply(somethingStupid::doSomethingStupid, left, right, epsilon);
            result.setX(x);
            result.setResult(somethingStupid.doSomethingStupid(x));
            return result;
        });
    }

    public static NorthWest run(final Algorithm algorithm, final StupidFunction variant, final double epsilon) {
        return algorithm.f.apply(variant, epsilon);
    }

    public static final Algorithm DICHOTOMY = unwrapAlgo("DICHOTOMY", (f, left, right, epsilon) -> {
        double x;
        do {
            x = getMiddle(left, right);
            final double f1 = f.apply(x - epsilon / 2);
            final double f2 = f.apply(x + epsilon / 2);
            if (f1 < f2) {
                right = x;
            } else {
                left = x;
            }
        } while (checkBounds(left, right, epsilon));
        return x;
    });

    private static double getMiddle(final double a, final double b) {
        return (a - b) / 2 + b;
    }

    private static boolean checkBounds(final double left, final double right, final double epsilon) {
        return Math.abs(left - right) >= epsilon;
    }

    private static final double REVERSED_GOLDEN_CONST = (Math.sqrt(5) - 1) / 2;

    public static final Algorithm GOLDEN_SECTION = unwrapAlgo("GOLDEN_SECTION", (f, left, right, epsilon) -> {
        double delta = (right - left) * REVERSED_GOLDEN_CONST;

        double x2 = left + delta;
        double x1 = right - delta;

        double f2 = f.apply(x2);
        double f1 = f.apply(x1);

        do {
            delta = REVERSED_GOLDEN_CONST * delta;
            if (f1 >= f2) {
                left = x1; x1 = x2; x2 = left + delta;
                f1 = f2; f2 = f.apply(x2);
            } else {
                right = x2; x2 = x1; x1 = right - delta;
                f2 = f1; f1 = f.apply(x1);
            }
        } while (delta > epsilon);
        return getMiddle(left, right);
    });

    private static final List<Double> FIBONACCI_NUMBERS = getNFibonacci();

    public static final Algorithm FIBONACCI = unwrapAlgo("FIBONACCI", (f, left, right, epsilon) -> {
        final int n = calculateFibonacciConst(left, right, epsilon);
        int k = 0;
        double lambda = getFibonacciVar(left, right, n, k + 2, k);
        double mu = getFibonacciVar(left, right, n, k + 1, k);
        double f_mu = f.apply(mu), f_lambda = f.apply(lambda);

        double an, bn;
        while (true) {
            k++;
            if (k == n - 2) {
                mu = lambda + epsilon;
                if (f_mu >= f_lambda) {
                    an = lambda;
                    bn = right;
                } else {
                    an = left;
                    bn = mu;
                }
                break;
            }
            if (f_lambda > f_mu) {
                left = lambda;
                lambda = mu;
                f_lambda = f_mu;
                mu = getFibonacciVar(left, right, n, k + 1, k);
                f_mu = f.apply(mu);
            } else {
                right = mu;
                mu = lambda;
                f_mu = f_lambda;
                lambda = getFibonacciVar(left, right, n, k + 2, k);
                f_lambda = f.apply(lambda);
            }
        }
        return getMiddle(an, bn);
    });

    private static int calculateFibonacciConst(final double left, final double right, final double epsilon) {
        return Math.min(1475, Math.abs(Collections.binarySearch(FIBONACCI_NUMBERS, (right - left) / epsilon)) + 1);
    }


    private static double getFibonacciVar(final double a, final double b, final int n, final int i, final int j) {
        return a + FIBONACCI_NUMBERS.get(n - i) / FIBONACCI_NUMBERS.get(n - j) * (b - a);
    }

    private static List<Double> getNFibonacci() {
        final List<Double> arr = new ArrayList<>(1476);
        arr.add(1.0);
        arr.add(1.0);
        for (int i = 2; i < 1476; i++) {
            arr.add(arr.get(i - 1) + arr.get(i - 2));
        }
        return arr;
    }

    public static final Algorithm PARABOLIC = unwrapAlgo("PARABOLIC", (f, a, c, epsilon) -> {
        double b = getMiddle(a, c), x;
        double fa = f.apply(a), fb = f.apply(b), fc = f.apply(c);
        while (checkBounds(a, c, epsilon)) {
            x = parabolicMinimum(a, b, c, fa, fb, fc);
            double fx = f.apply(x);
            if (fx < fb) {
                if (x < b) {
                    c = b;
                    fc = fb;
                } else {
                    a = b;
                    fa = fb;
                }
                b = x;
                fb = fx;
            } else {
                if (x < b) {
                    a = x;
                    fa = fx;
                } else {
                    c = x;
                    fc = fx;
                }
            }
        }
        return b;
    });

    private static double parabolicMinimum(
            final double a,
            final double b,
            final double c,
            final double fa,
            final double fb,
            final double fc
    ) {
        return b + 0.5 * ((fa - fb) * (c - b) * (c - b) - (fc - fb) * (b - a) * (b - a))
                / ((fa - fb) * (c - b) + (fc - fb) * (b - a));
    }

    public static final Algorithm BRENT = unwrapAlgo("BRENT", (f, a, c, epsilon) -> {
        double x, w, v, d, e, g, u, fx, fw, fv;
        x = w = v = a + REVERSED_GOLDEN_CONST * (c - a);
        fx = fw = fv = f.apply(x);
        d = e = c - a;
        while (checkBounds(a, c, epsilon)) {
            g = e;
            e = d;
            if (different(w, x, v) && different(fw, fx, fv)
                    && (u = parabolicMinimum(w, x, v, fw, fx, fv)) == u
                    && a <= u && u <= c && Math.abs(u - x) < (g / 2)) {
                // u - accepted
            } else  {
                // u - rejected, u - golden section
                if (x < getMiddle(a, c)) {
                    e = c - x;
                    u = x + REVERSED_GOLDEN_CONST * e;
                } else {
                    e = x - a;
                    u = x - REVERSED_GOLDEN_CONST * e;
                }
            }
            double fu = f.apply(u);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    c = x;
                }
                v = w; w = x; x = u;
                fv = fw; fw = fx; fx = fu;
            } else {
                if (u >= x) {
                    c = u;
                } else {
                    a = u;
                }
                if (fu <= fw || w == x) {
                    v = w; w = u;
                    fv = fw; fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
            d = c - a;
        }
        return x;
    });

    private static boolean different(final double a, final double b, final double c) {
        return a != b && b != c && c != a;
    }

    private static void printBounds(final double left, final double right) {
        System.out.format("[%s, %s]\n", left, right);
    }

    public static final Map<String, Algorithm> ALGORITHMS = new TreeMap<>();

    static {
        ALGORITHMS.put("DICHOTOMY", DICHOTOMY);
        ALGORITHMS.put("GOLDEN SECTION", GOLDEN_SECTION);
        ALGORITHMS.put("FIBONACCI", FIBONACCI);
        ALGORITHMS.put("PARABOLIC", PARABOLIC);
        ALGORITHMS.put("BRENT", BRENT);
    }

    @FunctionalInterface
    interface QuinaryFunction<A, B, C, D, R> {
        R apply(A a, B b, C c, D d);
    }

    private interface OptimizationAlgorithm extends QuinaryFunction<
            Function<Double, Double>,
            Double,
            Double,
            Double,
            Double
            > { }

    private record Algorithm(BiFunction<StupidFunction, Double, NorthWest> f) { }
}
