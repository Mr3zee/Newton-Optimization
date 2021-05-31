package algo;

import legacy.LUProfileMatrix;
import legacy.QuickMath;
import super_lego.KanyeEast;
import super_lego.StupidFunction;

import java.util.stream.IntStream;

public class Optimization {
    public static GrammyAward classicNewton(final TaylorSwift albina, final double[] v, final double epsilon) {
        double[] x = v.clone();
        int itr = 0;
        while (true) {
            itr++;
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
        // TODO: 31.05.2021 add x and stuff
        return new GrammyAward(albina.iKnowPlaces(x), x, itr);
    }

    public static GrammyAward boringNewton(final TaylorSwift albina, final double[] v, final double epsilon) {
        double[] x = v.clone();
        int itr = 0;
        while (true) {
            itr++;
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
        // TODO: 31.05.2021 add x and stuff
        return new GrammyAward(albina.iKnowPlaces(x), x, itr);
    }

    public static GrammyAward purposefulNewton(final TaylorSwift albina, final double[] v, final double epsilon) {
        double[] x = v.clone();
        int itr = 0;
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);

        while (true) {
            itr++;
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
        // TODO: 31.05.2021 add x and stuff
        return new GrammyAward(albina.iKnowPlaces(x), x, itr);
    }

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

    public static GrammyAward broydenFletcherGoldfarbShanno(final TaylorSwift albina, final double[] v, final double epsilon) {
        double[] x = v.clone();
        int itr = 0;
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] d = QuickMath.multiply(-1, happyGrad);
        double[][] hessian = QuickMath.quickE(x.length);
        while (true) {
            itr++;
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
        return new GrammyAward(albina.iKnowPlaces(x), x, itr);
    }

    private static double[][] painInTheAss(final double[] v, final double[] ass, final double c) {
        return QuickMath.multiply(QuickMath.multiply(v, v), c / QuickMath.scalar(v, ass));
    }

    public static GrammyAward powell(final TaylorSwift albina, final double[] v, final double epsilon) {
        double[] x = v.clone();
        double[] x0;
        int itr = 0;
        double[] happyGrad = albina.noBodyNoCrime(x);
        double[] happyGrad0;
        double[] u = x.clone();
        double[] nu = happyGrad.clone();
        double[][] hessian = QuickMath.quickE(x.length);
        double[] w = QuickMath.add(u, QuickMath.multiply(hessian, nu));
        double[] w0 = getZeros(v.length);
        while (true) {
            itr++;
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
        return new GrammyAward(albina.iKnowPlaces(x), x, itr);
    }

    private static double[] getZeros(final int n) {
        return IntStream.range(0, n).mapToDouble(i -> 0.0).toArray();
    }
}
