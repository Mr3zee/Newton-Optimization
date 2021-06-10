package algo;

import legacy.LUProfileMatrix;
import legacy.QuickMath;
import super_lego.KanyeEast;
import super_lego.NorthWest;
import super_lego.StupidFunction;

public class Optimization {

    @FunctionalInterface
    private interface Slave {
        double[] doAllTheWork(
                final TaylorSwift albina,
                final double[] start,
                final double epsilon,
                final Complexity complexity
        );
    }

    private static AWayToSuccess unwrapAlgo(final Slave slave) {
        return (albina, start, epsilon) -> {
            final Complexity complexity = new Complexity(start.length);
            final double[] x = slave.doAllTheWork(albina, start, epsilon, complexity);
            return new GrammyAward(albina.iKnowPlaces(x), x, complexity);
        };
    }

    public static final AWayToSuccess CLASSIC_NEWTON = unwrapAlgo((albina, x, epsilon, complexity) -> {
        while (true) {
            complexity.incItr();
            final double[] sadGrad = QuickMath.multiply(-1, albina.noBodyNoCrime(x));
            final double[][] hessian = albina.coneyIsland(x);
            final double[] ass = solveSoLE(hessian, sadGrad);
            x = QuickMath.add(x, ass);

            complexity.addComplexity(3, 1, 0);
            complexity.addCalls(0, 1, 1);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess BORING_NEWTON = unwrapAlgo((albina, x, epsilon, complexity) -> {
        while (true) {
            complexity.incItr();
            final double[] sadGrad = QuickMath.multiply(-1, albina.noBodyNoCrime(x));
            final double[][] hessian = albina.coneyIsland(x);
            final double[] d = solveSoLE(hessian, sadGrad);
            final double r = oneDimensionalOptimization(albina, epsilon, x, d, complexity);
            final double[] ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);

            complexity.addComplexity(4, 1, 1);
            complexity.addCalls(0,1, 1);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess PURPOSEFUL_NEWTON = unwrapAlgo((albina, x, epsilon, complexity) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);

        complexity.incItr();
        double r = oneDimensionalOptimization(albina, epsilon, x, d, complexity);
        double[] ass = QuickMath.multiply(r, d);

        x = QuickMath.add(x, ass);

        complexity.addLinearComplexity(2);
        complexity.gradCallsInc();
        do {
            complexity.addItr(1);
            happyGrad = albina.noBodyNoCrime(x);
            d = QuickMath.multiply(-1, happyGrad);

            final double[][] hessian = albina.coneyIsland(x);
            ass = solveSoLE(hessian, d);
            if (QuickMath.scalar(ass, happyGrad) < 0) {
                d = ass;
            }
            r = oneDimensionalOptimization(albina, epsilon, x, d, complexity);
            ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);

            complexity.addComplexity(7, 1, 1);
            complexity.addCalls(0, 1, 1);
        } while (!(QuickMath.norm(ass) < epsilon));
        return x;
    });

    private static double[] solveSoLE(final double[][] hessian, final double[] b) {
        final LUProfileMatrix matrix = LUProfileMatrix.createFromDense(hessian, b);
        matrix.upgrade();
        return matrix.solve();
    }

    private static double oneDimensionalOptimization(
            final TaylorSwift albina,
            final double epsilon,
            final double[] x,
            final double[] d,
            final Complexity complexity
    ) {
        final double[] projectX = x.clone();
        final NorthWest north = KanyeEast.run(KanyeEast.GOLDEN_SECTION, new StupidFunction(
                lambda -> albina.iKnowPlaces(QuickMath.add(projectX, QuickMath.multiply(lambda, d))),
                -20,
                // FIXME: 31.05.2021 why not
                20
        ), epsilon);
        complexity.addInnerItr(north.getItr());
        complexity.addLinearComplexity(north.getItr() + 2);
        complexity.addCalls(north.getItr(), 0, 0);
        return north.getX();
    }

    public static final AWayToSuccess BROYDEN_FLETCHER_GOLDFARB_SHANNO = unwrapAlgo((albina, x, epsilon, complexity) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);
        double[][] hessian = QuickMath.quickE(x.length);

        complexity.addCalls(0, 1, 0);
        complexity.addComplexity(2, 1, 0);
        while (true) {
            complexity.incItr();
            double r = oneDimensionalOptimization(albina, epsilon, x, d, complexity);
            double[] ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);
            double[] doppelganger = happyGrad.clone();
            happyGrad = albina.noBodyNoCrime(x);
            double[] p = QuickMath.subtract(happyGrad, doppelganger);
            double[] v = QuickMath.multiply(hessian, ass);
            double[][] pp = painInTheAss(p, ass, 1);
            double[][] vv = painInTheAss(v, ass, -1);
            hessian = QuickMath.add(hessian, pp);
            hessian = QuickMath.add(hessian, vv);
            d = solveSoLE(hessian, QuickMath.multiply(-1, happyGrad));

            complexity.addComplexity(10, 1, 1);
            complexity.addCalls(0, 1, 0);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    private static double[][] painInTheAss(final double[] v, final double[] ass, final double c) {
        return QuickMath.multiply(QuickMath.multiply(v, v), c / QuickMath.scalar(v, ass));
    }

    public static final AWayToSuccess POWELL = unwrapAlgo((albina, x, epsilon, complexity) -> {
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] happyGrad0;
        double[][] hessian = QuickMath.quickE(x.length);
        double[] d = QuickMath.multiply(-1, happyGrad);

        complexity.addComplexity(2, 1, 0);
        complexity.addCalls(0, 1, 0);
        while (true) {
            complexity.incItr();
            double r = oneDimensionalOptimization(albina, epsilon, x, d, complexity);
            double[] ass = QuickMath.multiply(r, d);
            x = QuickMath.add(x, ass);
            happyGrad0 = happyGrad.clone();
            happyGrad = albina.noBodyNoCrime(x);
            double[] p = QuickMath.subtract(happyGrad, happyGrad0);
            double[] u = QuickMath.subtract(ass, QuickMath.multiply(hessian, p));
            hessian = QuickMath.add(hessian, painInTheAss(u, p, 1));
            d = QuickMath.multiply(-1, QuickMath.multiply(hessian, happyGrad));

            complexity.addComplexity(11, 2, 0);
            complexity.addCalls(0, 1, 0);
            if (QuickMath.norm(ass) < epsilon) {
                break;
            }
        }
        return x;
    });

    public static final AWayToSuccess MARQUARDT = unwrapAlgo((albina, x, epsilon, complexity) -> {
        double alpha0 = 100;
        double alpha = 100;
        final double beta = 0.5;
        double fx = albina.iKnowPlaces(x);
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[][] hessian = albina.coneyIsland(x);

        complexity.addCalls(1, 1, 1);
        complexity.addComplexity(1, 1, 0);
        while (true) {
            complexity.incItr();
            double[] ass = solveSoLE(
                    QuickMath.add(hessian, QuickMath.multiply(QuickMath.quickE(x.length), alpha)),
                    QuickMath.multiply(-1, happyGrad)
            );
            double[] y = QuickMath.add(x, ass);
            double fy = albina.iKnowPlaces(y);

            complexity.fCallsInc();
            complexity.addComplexity(5, 2, 1);
            if (fy >= fx) {
                alpha /= beta;
            } else {
                x = y.clone();
                fx = fy;
                alpha0 *= beta;

                complexity.addLinearComplexity();
                if (QuickMath.norm(ass) < epsilon) {
                    break;
                }
                happyGrad = albina.noBodyNoCrime(x);
                hessian = albina.coneyIsland(x);
                alpha = alpha0;

                complexity.addCalls(0, 1, 1);
                complexity.addComplexity(1, 1, 0);
            }
        }
        return x;
    });

    public static final AWayToSuccess MARQUARDT_CHOLESKY = unwrapAlgo((albina, x, epsilon, complexity) -> {
        double alpha0 = 100;
        double alpha = 100;
        final double beta = 0.5;
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[][] hessian = albina.coneyIsland(x);

        complexity.addComplexity(1, 1, 0);
        complexity.addCalls(0, 1, 1);
        while (true) {
            complexity.incItr();
            final double[][] superHessian = QuickMath.add(hessian, QuickMath.multiply(QuickMath.quickE(x.length), alpha));
            final double[] ass = solveSoLE(
                    superHessian,
                    QuickMath.multiply(-1, happyGrad)
            );

            complexity.addComplexity(5, 2, 1);
            if (!choleskyDecompositionAvailable(superHessian)) {
                alpha = Math.max(alpha * 2, 1);
            } else {
                x = QuickMath.add(x, ass);
                alpha0 *= beta;

                complexity.addLinearComplexity();
                if (QuickMath.norm(ass) < epsilon) {
                    break;
                }
                happyGrad = albina.noBodyNoCrime(x);
                hessian = albina.coneyIsland(x);
                alpha = alpha0;

                complexity.addCalls(0, 1, 1);
                complexity.addComplexity(1, 1, 0);
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
            if (matrix[k][k] <= 0.0) {
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
