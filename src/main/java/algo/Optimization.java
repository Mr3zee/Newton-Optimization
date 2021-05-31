package algo;

import legacy.LUProfileMatrix;
import legacy.QuickMath;
import super_lego.KanyeEast;
import super_lego.StupidFunction;

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
            final LUProfileMatrix matrix = LUProfileMatrix.createFromDense(hessian, sadGrad);
            matrix.upgrade();
            final double[] d = matrix.solve();
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
            final LUProfileMatrix matrix = LUProfileMatrix.createFromDense(hessian, d);
            matrix.upgrade();
            ass = matrix.solve();
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

    private static double rapGod(final TaylorSwift albina, final double epsilon, final double[] x, final double[] d) {
        final double[] projectX = x.clone();
        return KanyeEast.run(KanyeEast.BRENT, new StupidFunction(
                lambda -> albina.iKnowPlaces(QuickMath.add(projectX, QuickMath.multiply(lambda, d))),
                0,
                // FIXME: 31.05.2021 why not
                10
        ), epsilon).getX();
    }
}
