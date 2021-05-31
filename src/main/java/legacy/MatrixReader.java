package legacy;

public class MatrixReader implements AutoCloseable {
    private final int n;
    private final double[][] matrix;
    private int i;

    public MatrixReader(final double[][] matrix) {
        this.n = matrix.length;
        this.matrix = matrix;
        this.i = 0;
    }

    public int n() {
        return n;
    }

    public double[] next() {
        return matrix[i++];
    }

    @Override
    public void close() { }
}
