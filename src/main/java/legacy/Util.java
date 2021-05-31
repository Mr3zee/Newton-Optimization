package legacy;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Util {
    @FunctionalInterface
    public interface Getter {
        double get(int i, int j);
    }

    public static void outMatrix(int n, final Getter getter) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(getter.get(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void printVector(final double[] vector) {
        System.out.println("[" + Arrays.stream(vector).mapToObj(String::valueOf).collect(Collectors.joining(", ")) + "]\n");
    }

}
