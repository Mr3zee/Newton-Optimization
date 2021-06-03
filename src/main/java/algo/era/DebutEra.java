package algo.era;

import algo.TaylorSwift;

public class DebutEra implements TaylorSwift {
    @Override
    public double iKnowPlaces(double[] v) {
        return 50 * v[0] * v[0] + v[1] * v[1] + 20 * v[0] + 20 * v[1] + 239;
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        return new double[]{
                20 * (5 * v[0] + 1),
                2 * (v[1] + 10)
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][] {
                {100, 0},
                {0, 2}
        };
    }
}
