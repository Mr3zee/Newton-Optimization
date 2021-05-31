import algo.era.RedEra;
import algo.era.SpeakNowEra;
import algo.GrammyAward;
import algo.Optimization;
import algo.TaylorSwift;
import static legacy.Util.printVector;

public class Main {
    public static void main(String[] args) {
        final double[] start = new double[] {-1.2, 1};
        final double epsilon = 1E-7;
        final TaylorSwift taylor = new RedEra();
        final GrammyAward award1 = Optimization.classicNewton(taylor, start, epsilon);
        final GrammyAward award2 = Optimization.boringNewton(taylor, start, epsilon);
        final GrammyAward award3 = Optimization.purposefulNewton(taylor, start, epsilon);
        printVector(award1.x());
        System.out.println(award1.y());
        printVector(award2.x());
        System.out.println(award2.y());
        printVector(award3.x());
        System.out.println(award3.y());
    }
}
