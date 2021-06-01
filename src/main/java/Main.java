import algo.AWayToSuccess;
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
        run(Optimization.CLASSIC_NEWTON, taylor, start, epsilon);
        run(Optimization.BORING_NEWTON, taylor, start, epsilon);
        run(Optimization.PURPOSEFUL_NEWTON, taylor, start, epsilon);
        run(Optimization.BROYDEN_FLETCHER_GOLDFARB_SHANNO, taylor, start, epsilon);
        run(Optimization.POWELL, taylor, start, epsilon);
        run(Optimization.MARQUARDT, taylor, start, epsilon);
        run(Optimization.MARQUARDT_CHOLESKY, taylor, start, epsilon);
        System.out.println("=".repeat(100));
    }

    private static void run(final AWayToSuccess way, final TaylorSwift taylorSwift, final double[] start, final double eps) {
        System.out.print("Starting... ");
        final GrammyAward award = way.tryMe(taylorSwift, start, eps);
        System.out.println("OK");
        System.out.print("x vector: ");
        printVector(award.x());
        System.out.format("y min: %s%nitr: %s%n%n", award.y(), award.itr());
    }
}
