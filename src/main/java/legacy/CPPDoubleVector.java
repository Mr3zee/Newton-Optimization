package legacy;

import java.util.Arrays;

public class CPPDoubleVector {
    private double[] vector;
    private int size;
    private static final double bloatFactor = 1.5;

    public CPPDoubleVector(int initCapacity) {
        this.size = 0;
        this.vector = new double[initCapacity];
    }

    public CPPDoubleVector() {
        this(10);
    }

    public void add(double element) {
        set(size++, element);
    }

    public double get(int index) throws IndexOutOfBoundsException {
        return vector[index];
    }

    public void set(int index, double value) {
        if (index >= vector.length) {
            increaseCapacity((int) (index * bloatFactor));
        }
        vector[index] = value;
    }

    private void increaseCapacity(int newSize) {
        double[] temp = new double[newSize];
        System.arraycopy(vector, 0, temp, 0, vector.length);
        vector = temp;
    }

    public double[] toArray() {
        return Arrays.copyOf(vector, size);
    }

    public int size() {
        return size;
    }
}
