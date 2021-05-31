package legacy;

public interface SuperDuperMatrix {
    double get(int i, int j);

    double[] solve();

    void upgrade();

    boolean isUpgraded();

    int size();

    double[] getB();

    default void checkUpgraded() {
        check(isUpgraded(), "Matrix is not upgraded");
    }

    private static void check(boolean flag, String message) {
        if (!flag) {
            throw new IllegalStateException(message);
        }
    }

    default void checkNotUpgraded() {
        check(!isUpgraded(), "Matrix is already upgraded");
    }
}
