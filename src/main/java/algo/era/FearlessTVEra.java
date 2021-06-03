package algo.era;

import algo.TaylorSwift;

public class FearlessTVEra implements TaylorSwift {

    // v.length == 100
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
        return new double[0];
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[0][];
    }
}
