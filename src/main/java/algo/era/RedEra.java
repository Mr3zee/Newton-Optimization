package algo.era;

import algo.TaylorSwift;

public class RedEra implements TaylorSwift {
    @Override
    public double iKnowPlaces(double[] v) {
        return 100 * (v[1] - v[0] * v[0]) * (v[1] - v[0] * v[0]) + (1 - v[1]) * (1 - v[1]);
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        return new double[]{400 * v[0] * (v[0] * v[0] - v[1]), -200 * v[0] * v[0] + 202 * v[1] - 2};
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][]{
                {800 * v[0] * v[0] - 400 * (v[1] - v[0] * v[0]), -400 * v[0]},
                {-400 * v[0], 202}
        };
    }
}
