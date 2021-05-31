package super_lego;

import java.util.function.DoubleUnaryOperator;

public class StupidFunction {
    private final DoubleUnaryOperator f;
    private final double left;
    private final double right;

    public StupidFunction(final DoubleUnaryOperator f, final double left, final double right) {
        this.f = f;
        this.left = left;
        this.right = right;
    }

    public double doSomethingStupid(final double v) {
        return f.applyAsDouble(v);
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
