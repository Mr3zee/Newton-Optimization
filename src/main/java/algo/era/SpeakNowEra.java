package algo.era;

import algo.TaylorSwift;

public class SpeakNowEra implements TaylorSwift {

    @Override
    public double iKnowPlaces(final double[] v) {
        return v[0] * v[0] + v[1] * v[1] - 1.2 * v[0] * v[1];
    }

    @Override
    public double[] noBodyNoCrime(final double[] v) {
        return new double[] {2 * v[0] - 1.2 * v[1], 2 * v[1] - 1.2 * v[0]};
    }

    @Override
    public double[][] coneyIsland(final double[] v) {
        return new double[][] {
                {2, -1.2},
                {-1.2, 2}
        };
    }
}
