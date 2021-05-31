import algo.era.RedEra;
import algo.GrammyAward;
import algo.Optimization;
import algo.TaylorSwift;

import static legacy.Util.printVector;

public class Main {
    public static void main(String[] args) {
        final double[] start = new double[] {-1.2, 1};
        final double epsilon = 1E-7;
        final TaylorSwift taylor = new RedEra();
        run(Optimization::classicNewton, taylor, start, epsilon);
        run(Optimization::boringNewton, taylor, start, epsilon);
        run(Optimization::purposefulNewton, taylor, start, epsilon);
        run(Optimization::broydenFletcherGoldfarbShanno, taylor, start, epsilon);
    }

    private static void run(final Run run, final TaylorSwift taylorSwift, final double[] start, final double eps) {
        System.out.print("Starting... ");
        final GrammyAward award = run.run(taylorSwift, start, eps);
        System.out.println("OK");
        System.out.print("x vector: ");
        printVector(award.x());
        System.out.format("y min: %s, itr: %s%n", award.y(), award.itr());
    }

    @FunctionalInterface
    private interface Run {
        GrammyAward run(final TaylorSwift taylorSwift, final double[] start, final double eps);
    }
}
