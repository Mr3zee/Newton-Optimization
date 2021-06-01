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
        run("CLASSIC_NEWTON", Optimization.CLASSIC_NEWTON, taylor, start, epsilon);
        run("BORING_NEWTON", Optimization.BORING_NEWTON, taylor, start, epsilon);
        run("PURPOSEFUL_NEWTON", Optimization.PURPOSEFUL_NEWTON, taylor, start, epsilon);
        run("BROYDEN_FLETCHER_GOLDFARB_SHANNO", Optimization.BROYDEN_FLETCHER_GOLDFARB_SHANNO, taylor, start, epsilon);
        run("POWELL", Optimization.POWELL, taylor, start, epsilon);
        run("MARQUARDT", Optimization.MARQUARDT, taylor, start, epsilon);
        run("MARQUARDT_CHOLESKY", Optimization.MARQUARDT_CHOLESKY, taylor, start, epsilon);
        System.out.println("=".repeat(100));
    }

    private static void run(final String name, final AWayToSuccess way, final TaylorSwift taylorSwift, final double[] start, final double eps) {
        System.out.format("Starting %s... ", name);
        final GrammyAward award = way.tryMe(taylorSwift, start, eps);
        System.out.println("OK");
        System.out.print("x vector: ");
        printVector(award.x());
        System.out.format(
                "y min: %s%nitr: %s%ninnerItr: %s%ncomplexity: %s%n%n",
                award.y(),
                award.complexity().getItr(),
                award.complexity().getInnerItr(),
                award.complexity().getComplexity()
        );
    }
}
