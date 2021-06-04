package algo.era;

import algo.TaylorSwift;

public class FolkloreEra implements TaylorSwift {

    // (x+10y)^2+5(z-p)^2+(y-2z)^4+10(x-p)^4
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
                2 * (20 * Math.pow(v[0] - v[3], 3) + v[0] + 10 * v[1]),
                4 * (5 * (v[0] + 10 * v[1]) + Math.pow(v[1] - 2 * v[2], 3)),
                10 * (v[2] - v[3]) - 8 * Math.pow(v[1] - 2 * v[2], 3),
                10 * (-4 * Math.pow(v[0] - v[3], 3) + v[3] - v[0])
        };
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[][] {
                {
                    2 * (60 * Math.pow(v[0] - v[3], 2) + 1),
                    20,
                    0,
                    -120 * Math.pow(v[0] - v[3], 2)
                },
                {
                    20,
                    4 * (50 + 3 * Math.pow(v[1] - 2 * v[2], 2)),
                    -24 * Math.pow(v[1] - 2 * v[2], 2),
                    0
                },
                {
                    0,
                    -24 * Math.pow(v[1] - 2 * v[2], 2),
                    10 + 48 * Math.pow(v[1] - 2 * v[2], 2),
                    -10
                },
                {
                    -120 * Math.pow(v[0] - v[3], 2),
                    0,
                    -10,
                    10 * (12 * Math.pow(v[0] - v[3], 2) + 1)
                }
        };
    }
}
