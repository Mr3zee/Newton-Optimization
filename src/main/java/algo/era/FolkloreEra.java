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
        return new double[0];
    }

    @Override
    public double[][] coneyIsland(double[] v) {
        return new double[0][];
    }
}
