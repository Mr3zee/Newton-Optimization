package algo;

import legacy.LUProfileMatrix;
import legacy.QuickMath;
import super_lego.KanyeEast;
import super_lego.StupidFunction;

import java.util.concurrent.atomic.AtomicInteger;

public class Optimization {

    @FunctionalInterface
    private interface Slave {
        double[] doAllTheWork(
                final TaylorSwift albina,
                final double[] start,
                final double epsilon,
                final AtomicInteger itr,
                final AtomicInteger innerIrt
        );
    }

    private static AWayToSuccess unwrapAlgo(final Slave slave) {
        return (albina, start, epsilon) -> {
            final AtomicInteger itr = new AtomicInteger();
            final AtomicInteger innerItr = new AtomicInteger();
            final double[] x = slave.doAllTheWork(albina, start, epsilon, itr, innerItr);
            return new GrammyAward(albina.iKnowPlaces(x), x, itr.get(), innerItr.get());
        };
    }

    public static final AWayToSuccess CLASSIC_NEWTON = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        while (true) {
            itr.incrementAndGet();
            final double[] sadGrad = QuickMath.multiply(-1, albina.noBodyNoCrime(x));
            final double[][] hessian = albina.coneyIsland(x);
            final LUProfileMatrix matrix = LUProfileMatrix.createFromDense(hessian, sadGrad);
            matrix.upgrade();
            final double[] ass = matrix.solve();
            x = QuickMath.add(x, ass);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess BORING_NEWTON = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        while (true) {
            itr.incrementAndGet();
            final double[] sadGrad = QuickMath.multiply(-1, albina.noBodyNoCrime(x));
            final double[][] hessian = albina.coneyIsland(x);
            final double[] d = solveSoLE(hessian, sadGrad);
            final double r = rapGod(albina, epsilon, x, d);
            final double[] ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess PURPOSEFUL_NEWTON = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);

        while (true) {
            itr.incrementAndGet();
            double r = rapGod(albina, epsilon, x, d);
            double[] ass = QuickMath.multiply(r, d);

            x = QuickMath.add(x, ass);
            happyGrad = albina.noBodyNoCrime(x);
            d = QuickMath.multiply(-1, happyGrad);

            final double[][] hessian = albina.coneyIsland(x);
            ass = solveSoLE(hessian, d);
            if (QuickMath.scalar(ass, happyGrad) < 0) {
                d = ass;
            }
            r = rapGod(albina, epsilon, x, d);
            ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    private static double[] solveSoLE(final double[][] hessian, final double[] b) {
        final LUProfileMatrix matrix = LUProfileMatrix.createFromDense(hessian, b);
        matrix.upgrade();
        return matrix.solve();
    }

    private static double rapGod(final TaylorSwift albina, final double epsilon, final double[] x, final double[] d) {
        final double[] projectX = x.clone();
        return KanyeEast.run(KanyeEast.PARABOLIC, new StupidFunction(
                lambda -> albina.iKnowPlaces(QuickMath.add(projectX, QuickMath.multiply(lambda, d))),
                0,
                // FIXME: 31.05.2021 why not
                10
        ), epsilon).getX();
    }

    public static final AWayToSuccess BROYDEN_FLETCHER_GOLDFARB_SHANNO = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);
        double[][] hessian = QuickMath.quickE(x.length);
        while (true) {
            itr.incrementAndGet();
            double r = rapGod(albina, epsilon, x, d);
            double[] ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);
            double[] doppelganger = happyGrad.clone();
            happyGrad = albina.noBodyNoCrime(x);
            double[] p = QuickMath.subtract(happyGrad, doppelganger);
            double[] actualV = QuickMath.multiply(hessian, ass);
            double[][] pp = painInTheAss(p, ass, 1);
            double[][] vv = painInTheAss(actualV, ass, -1);
            hessian = QuickMath.add(hessian, pp);
            hessian = QuickMath.add(hessian, vv);
            d = solveSoLE(hessian, QuickMath.multiply(-1, happyGrad));
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    private static double[][] painInTheAss(final double[] v, final double[] ass, final double c) {
        return QuickMath.multiply(QuickMath.multiply(v, v), c / QuickMath.scalar(v, ass));
    }

    public static final AWayToSuccess POWELL = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] happyGrad0;
        double[] u = x.clone();
        double[] nu = happyGrad.clone();
        double[][] hessian = QuickMath.quickE(x.length);
        double[] w = QuickMath.add(u, QuickMath.multiply(hessian, nu));
        double[] w0 = QuickMath.getZeros(x.length);
        double[] x0;
        while (true) {
            itr.incrementAndGet();
            double r = rapGod(albina, epsilon, x, happyGrad);
            double[] ass = QuickMath.multiply(r, happyGrad);
            x0 = x;
            x = QuickMath.add(x, ass);
            hessian = QuickMath.add(hessian, painInTheAss(QuickMath.subtract(w, w0), nu, -1));
            u = QuickMath.subtract(x, x0);
            happyGrad0 = happyGrad;
            happyGrad = albina.noBodyNoCrime(x);
            nu = QuickMath.subtract(happyGrad, happyGrad0);
            w0 = w;
            w = QuickMath.add(u, QuickMath.multiply(hessian, nu));
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess MARQUARDT = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        double alpha0 = 100;
        double alpha = 100;
        final double beta = 0.5;
        double fx = albina.iKnowPlaces(x);
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[][] hessian = albina.coneyIsland(x);
        while (true) {
            itr.incrementAndGet();
            double[] ass = solveSoLE(
                    QuickMath.add(hessian, QuickMath.multiply(QuickMath.quickE(x.length), alpha)),
                    QuickMath.multiply(-1, happyGrad)
            );
            double[] y = QuickMath.add(x, ass);
            double fy = albina.iKnowPlaces(y);
            if (fy >= fx) {
                alpha /= beta;
            } else {
                x = y.clone();
                fx = fy;
                alpha0 *= beta;
                if (QuickMath.norm(ass) < epsilon) {
                    break;
                }
                happyGrad = albina.noBodyNoCrime(x);
                hessian = albina.coneyIsland(x);
                alpha = alpha0;
            }
        }
        return x;
    });

    public static final AWayToSuccess MARQUARDT_CHOLESKY = unwrapAlgo((albina, x, epsilon, itr, innerItr) -> {
        double alpha0 = 100;
        double alpha = 100;
        final double beta = 0.5;
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[][] hessian = albina.coneyIsland(x);
        while (true) {
            itr.incrementAndGet();
            final double[][] superHessian = QuickMath.add(hessian, QuickMath.multiply(QuickMath.quickE(x.length), alpha));
            final double[] ass = solveSoLE(
                    superHessian,
                    QuickMath.multiply(-1, happyGrad)
            );
            if (!choleskyDecompositionAvailable(superHessian)) {
                alpha = Math.max(alpha * 2, 1);
            } else {
                x = QuickMath.add(x, ass);
                alpha0 *= beta;
                if (QuickMath.norm(ass) < epsilon) {
                    break;
                }
                happyGrad = albina.noBodyNoCrime(x);
                hessian = albina.coneyIsland(x);
                alpha = alpha0;
            }
        }
        return x;
    });

    private static boolean choleskyDecompositionAvailable(final double[][] matrix) {
        final int n = matrix.length;

        for (int k = 0; k < n; k++) {
            for (int p = 0; p < k; p++) {
                matrix[k][k] -= matrix[k][p] * matrix[k][p];
            }
            if (matrix[k][k] <= 0.0 ) {
                return false;
            }
            matrix[k][k] = Math.sqrt(matrix[k][k]);
            final double reciprocal = 1.0 / matrix[k][k];
            for (int i = k + 1; i < n; i++) {
                for (int p = 0; p < k; p++) {
                    matrix[i][k] -= matrix[i][p] * matrix[k][p];
                }
                matrix[i][k] *= reciprocal;
                matrix[k][i] *= matrix[i][k];
            }
        }
        return true;
    }
}
