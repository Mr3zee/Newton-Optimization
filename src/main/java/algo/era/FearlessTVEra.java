package algo.era;

import algo.TaylorSwift;
import legacy.QuickMath;

public class FearlessTVEra implements TaylorSwift {

    // v.length := 100
    @Override
    public double iKnowPlaces(double[] v) {
        double sum = 0;
        for (int i = 0; i < v.length - 1; i++) {
            sum += 100 * Math.pow(v[i + 1] - v[i] * v[i], 2) + Math.pow(1 - v[i], 2);
        }
        return sum;
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        final int n = v.length;
        final double[] grad = new double[n];
        for (int i = 1; i < n - 1; i++) {
            grad[i] = 2 * (1 - v[i]) + 400 * (v[i + 1] - v[i] * v[i]) * v[i] + 200 * (v[i] - v[i - 1] * v[i - 1]);
        }
        grad[0] = 2 * (1 - v[0]) + 400 * (v[1] - v[0] * v[0]) * v[0];
        grad[n - 1] = 200 * (v[n - 1] - v[n - 2] * v[n - 2]);
        return grad;
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        final int n = v.length;
        final double[][] hessian = QuickMath.getDZeros(n);
        for (int i = 1; i < n - 1; i++) {
            hessian[i][i] = -2 + 400 * (v[i + 1] - 3 * v[i] * v[i]) + 200;
            hessian[i][i - 1] = -400 * v[i - 1];
            hessian[i][i + 1] = 400 * v[i];
        }
        hessian[0][0] = -2 + 400 * (v[1] - 3 * v[0] * v[0]);
        hessian[0][1] = 400 * v[0];

        hessian[n - 1][n - 1] = 200;
        hessian[n - 1][n - 2] = -400 * v[n - 2];
        return hessian;
    }
}
