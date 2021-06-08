import algo.TaylorSwift;
import algo.era.DebutEra;
import algo.era.ReputationEra;
import legacy.Util;

public class Test {
    public static void main(String[] args) {
        final TaylorSwift expected = new DebutEra();
        final TaylorSwift actual = new ReputationEra() {
            @Override
            public double iKnowPlaces(double[] v) {
                return expected.iKnowPlaces(v);
            }
        };
        final double[] v = new double[] {1, 2};
        final double[][] EHessian = expected.coneyIsland(v);
        final double[][] AHessian = actual.coneyIsland(v);

        System.out.print("Expected Gradient: ");
        Util.printVector(expected.noBodyNoCrime(v));
        System.out.print("  Actual Gradient: ");
        Util.printVector(actual.noBodyNoCrime(v));

        System.out.println("\nExpected Hessian:");
        Util.outMatrix(2, (i, j) -> EHessian[i][j]);
        System.out.println("Actual Hessian:");
        Util.outMatrix(2, (i, j) -> AHessian[i][j]);
    }
}
