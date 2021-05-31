package algo.era;

import algo.TaylorSwift;

public class $1989Era implements TaylorSwift {
    @Override
    public double iKnowPlaces(double[] v) {
        return Math.pow(v[0] * v[0] + v[1] - 11, 2) + Math.pow(v[0] + v[1] * v[1] - 7, 2);
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        return new double[] {
                2 * (2 * v[0] * (v[0] * v[0] + v[1] - 11) + v[0] + v[1] * v[1] - 7),
                2 * (2 * v[1] * (v[0] + v[1] * v[1] - 7) + v[0] * v[0] + v[1] - 11),
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][] {
                {4 * (v[0] * v[0] + v[1] - 11) + 8 * v[0] * v[0] + 2, 4 * v[0] + 4 * v[1]},
                {4 * v[0] + 4 * v[1], 4 * (v[0] + v[1] * v[1] - 7) + 8 * v[1] * v[1] + 2},
        };
    }
}
