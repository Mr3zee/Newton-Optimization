package algo.era;

import algo.TaylorSwift;

public class FearlessEra implements TaylorSwift {
    @Override
    public double iKnowPlaces(double[] v) {
        return Math.pow(v[0] - 2, 4) + Math.pow(v[1] - 3, 4) + Math.pow(v[2] - 4, 4);
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        return new double[]{
                4 * Math.pow(v[0] - 2, 3),
                4 * Math.pow(v[1] - 3, 3),
                4 * Math.pow(v[2] - 4, 3)
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][]{
                {12 * Math.pow(v[0] - 2, 2), 0, 0},
                {0, 12 * Math.pow(v[1] - 3, 2), 0},
                {0, 0, 12 * Math.pow(v[2] - 4, 2)},
        };
    }
}
