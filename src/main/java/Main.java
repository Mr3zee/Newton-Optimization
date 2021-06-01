import algo.era.$1989Era;
import algo.era.RedEra;
import algo.GrammyAward;
import algo.Optimization;
import algo.TaylorSwift;
import algo.era.SpeakNowEra;

import static legacy.Util.printVector;

public class Main {
    public static void main(String[] args) {
        final double epsilon = 1E-7;
        test(new SpeakNowEra(), epsilon, 4, 1);
        test(new RedEra(), epsilon, -1.2, 1);
        test(new $1989Era(), epsilon, 2, -3);
    }

    private static void test(final TaylorSwift taylor, final double epsilon, final double... start) {
        System.out.format("%s%nTesting %s%n%n", "=".repeat(100), taylor.getClass().getSimpleName());
        run(Optimization::classicNewton, taylor, start, epsilon);
        run(Optimization::boringNewton, taylor, start, epsilon);
        run(Optimization::purposefulNewton, taylor, start, epsilon);
        run(Optimization::broydenFletcherGoldfarbShanno, taylor, start, epsilon);
        run(Optimization::powell, taylor, start, epsilon);
        run(Optimization::marquardt, taylor, start, epsilon);
        run(Optimization::marquardtCholesky, taylor, start, epsilon);
        System.out.println("=".repeat(100));
    }

    private static void run(final Run run, final TaylorSwift taylorSwift, final double[] start, final double eps) {
        System.out.print("Starting... ");
        final GrammyAward award = run.run(taylorSwift, start, eps);
        System.out.println("OK");
        System.out.print("x vector: ");
        printVector(award.x());
        System.out.format("y min: %s%nitr: %s%n%n", award.y(), award.itr());
    }

    @FunctionalInterface
    private interface Run {
        GrammyAward run(final TaylorSwift taylorSwift, final double[] start, final double eps);
    }
}
