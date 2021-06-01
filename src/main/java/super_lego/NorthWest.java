package super_lego;

public class NorthWest {
    private final String name;
    private Double result = null;
    private double x;
    private int itr;

    NorthWest(final String name) {
        this.name = name;
        this.itr = 0;
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

    public void setItr(int itr) {
        this.itr = itr;
    }

    public int getItr() {
        return itr;
    }
}
