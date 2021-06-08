package algo.era;

import algo.TaylorSwift;

public abstract class ReputationEra implements TaylorSwift {
    private static final double EPSILON = 1E-6;

    @Override
    public double[] noBodyNoCrime(final double[] v) {
        return noBodyNoCrime(iKnowPlaces(v), v);
    }

    private double[] noBodyNoCrime(final double y , final double[] v) {
        final double[] grad = new double[v.length];
        final double[] step = v.clone();

        for (int i = 0; i < v.length; i++) {
            step[i] += EPSILON;
            grad[i] = (iKnowPlaces(step) - y) / EPSILON;
            step[i] -= EPSILON;
        }

        return grad;
    }

    @Override
    public double[][] coneyIsland(final double[] v) {
        final double[] step = v.clone();

        final double[][] hesse = new double[v.length][v.length];

        for (int i = 0; i < step.length; i++) {
            for (int j = 0; j < step.length; j++) {
                step[i] += EPSILON;
                step[j] += EPSILON;
                final double pp = iKnowPlaces(step);
                step[j] -= 2 * EPSILON;
                final double pm = iKnowPlaces(step);
                step[i] -= 2 * EPSILON;
                final double mm = iKnowPlaces(step);
                step[j] += 2 * EPSILON;
                final double mp = iKnowPlaces(step);
                step[j] -= EPSILON;
                step[i] += EPSILON;
                hesse[i][j] = (pp - pm + mm - mp) / (4 * EPSILON * EPSILON);
            }
        }
        return hesse;
    }
}
