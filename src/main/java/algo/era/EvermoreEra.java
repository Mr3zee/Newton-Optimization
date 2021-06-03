package algo.era;

import algo.TaylorSwift;

public class EvermoreEra implements TaylorSwift {
    @Override
    public double iKnowPlaces(double[] v) {
        return 100 -
                2 / (1 + Math.pow((v[0] - 1) / 2, 2) + Math.pow((v[1] - 1) / 3, 2)) -
                1 / (1 + Math.pow((v[0] - 2) / 2, 2) + Math.pow((v[1] - 1) / 3, 2));
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        double x = v[0];
        double y = v[1];
        return new double[] {
                648 * (x - 2) / Math.pow(9 * x * x - 36 * x + 4 * y * y - 8 * y + 76, 2)
                        + (x - 1) / Math.pow( 1.0 / 4.0 * Math.pow(x - 1, 2) + 1 / 9.0 * Math.pow(y - 1, 2) + 1, 2),
                2 / 9.0 * (y - 1) * (2 / Math.pow(1 / 4.0 * Math.pow(x - 1, 2) + 1 / 9.0 * Math.pow(y - 1, 2) + 1, 2)
                        + 1 / Math.pow(1 / 4.0 * Math.pow(x - 2, 2) + 1 / 9.0 * Math.pow(y - 1, 2) + 1, 2))
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[0][];
    }
}
