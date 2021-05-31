package super_lego;

public class NorthWest {
    private final String name;
    private Double result = null;
    private final double leftBound;
    private final double rightBound;
    private double x;

    NorthWest(final String name, final double leftBound, final double rightBound) {
        this.name = name;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public void setResult(final Double result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public double getResult() {
        return result;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
