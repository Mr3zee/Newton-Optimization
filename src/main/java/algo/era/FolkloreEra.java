package algo.era;

import algo.TaylorSwift;

public class FolkloreEra implements TaylorSwift {

    // (x+10y)^2+5(z-p)^2+(y-2z)^4+10(y-p)^4
    @Override
    public double iKnowPlaces(double[] v) {
        return Math.pow(v[0] + 10 * v[1], 2) +
                5 * Math.pow(v[2] - v[3], 2) +
                Math.pow(v[1] - 2 * v[2], 4) +
                10 * Math.pow(v[0] - v[3], 4);
    }

    @Override
    public double[] noBodyNoCrime(double[] v) {
        return new double[] {
                2 * (v[0] + 10 * v[1]),
                4 * (10 * Math.pow(v[1] - v[3], 3) + 5 * (v[0] + 10 * v[1]) + Math.pow(v[1] - 2 * v[2], 3)),
                10 * (v[2] - v[3]) - 8 * Math.pow(v[1] - 2 * v[2], 3),
                10 * (4 * Math.pow(v[3] - v[1], 3) + v[3] - v[2])
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][] {
                {
                    2,
                    20,
                    0,
                    0
                },
                {
                    20,
                    4 * (30 * Math.pow(v[1] - v[3], 2) + 50 + 3 * Math.pow(v[1] - 2 * v[2], 2)),
                    -24 * Math.pow(v[1] - 2 * v[2], 2),
                    -40 * Math.pow(v[1] - v[3], 2)
                },
                {
                    0,
                    -24 * Math.pow(v[1] - 2 * v[3], 2),
                    10 + 48 * Math.pow(v[1] - 2 * v[3], 2),
                    -10
                },
                {
                    0,
                    120 * Math.pow(v[3] - v[1], 2),
                    -10,
                    10 + 12 * Math.pow(v[3] - v[1], 2)
                }
        };
    }
}
