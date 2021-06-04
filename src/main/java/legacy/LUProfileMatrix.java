package legacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class LUProfileMatrix implements SuperDuperMatrix, Cloneable {
    private final int n;
    private final double[] diagonal;
    private final double[] rowProfile;
    private final double[] columnProfile;
    private final int[] indexes;
    private final double[] b;
    private int status;

    private boolean isLU;

    private LUProfileMatrix(
            final int n,
            final double[] diagonal,
            final double[] rowProfile,
            final double[] columnProfile,
            final int[] indexes,
            final double[] b
    ) {
        this.n = n;

        this.diagonal = diagonal;
        this.rowProfile = rowProfile;
        this.columnProfile = columnProfile;

        this.indexes = indexes;

        this.b = b;
        this.isLU = false;
        this.status = -1;
    }

    @Override
    public int size() {
        return n;
    }

    private void set(final int i, final int j, final double v) {
        checkNotUpgraded();
        checkBounds(i, j);
        if (j == i) {
            diagonal[i] = v;
            return;
        }
        if (j < i) {
            setByIndex(rowProfile, i, j, v);
        } else {
            setByIndex(columnProfile, j, i, v);
        }
    }

    private void setByIndex(final double[] profile, final int a, final int b, final double v) {
        int shift = getShift(a);
        if (b >= shift) {
            profile[getIndexInProfile(shift, a, b)] = v;
        }
    }

    @Override
    public double get(final int i, final int j) {
        checkNotUpgraded();
        checkBounds(i, j);
        if (j == i) {
            return diagonal[i];
        }
        if (j < i) {
            return getByIndex(rowProfile, i, j);
        }
        return getByIndex(columnProfile, j, i);
    }

    private void checkBounds(final int i, final int j) {
        if (!(0 <= i && i < n) || !(0 <= j && j < n)) {
            throw new IndexOutOfBoundsException(String.format("wrong indexes: i=%d, j=%d", i, j));
        }
    }

    private double getByIndex(final double[] profile, final int a, final int b) {
        int shift = getShift(a);
        if (b < shift) {
            return 0;
        } else {
            return profile[getIndexInProfile(shift, a, b)];
        }
    }

    public static LUProfileMatrix createFromDense(final double[][] matrix, final double[] b) {
        try (final MatrixReader reader = new MatrixReader(matrix)) {
            final int n = reader.n();
            final double[] diagonal = new double[n];

            final CPPDoubleVector rowProfile = new CPPDoubleVector();
            final CPPDoubleVector[] columnProfiles = new CPPDoubleVector[n];
            for (int i = 0; i < n; i++) {
                columnProfiles[i] = new CPPDoubleVector();
            }

            final int[] indexes = new int[n + 1];
            Arrays.fill(indexes, -1);

            final boolean[] foundNonZeroColumn = new boolean[n];
            Arrays.fill(foundNonZeroColumn, false);

            for (int i = 0; i < n; i ++) {
                double[] values = reader.next();
                boolean foundNonZeroRow = false;

                for (int j = 0; j < n; j++) {
                    if (j < i) {
                        if (foundNonZeroRow || !QuickMath.isZero(values[j])) {
                            foundNonZeroRow = true;
                            if (indexes[i] == -1) {
                                indexes[i] = rowProfile.size();
                            }
                            rowProfile.add(values[j]);
                        }
                    } else if (j > i) {
                        if (foundNonZeroColumn[j] || !QuickMath.isZero(values[j])) {
                            foundNonZeroColumn[j] = true;
                            columnProfiles[j].add(values[j]);
                        }
                    } else {
                        diagonal[i] = values[i];
                    }
                }

                if (indexes[i] == -1) {
                    indexes[i] = rowProfile.size();
                }
            }

            CPPDoubleVector columnProfile = new CPPDoubleVector(n + 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < columnProfiles[i].size(); j++) {
                    columnProfile.add(columnProfiles[i].get(j));
                }
            }
            indexes[n] = rowProfile.size();

            return new LUProfileMatrix(n, diagonal, rowProfile.toArray(), columnProfile.toArray(), indexes, b);
        }
    }

    public static LUProfileMatrix createFromProfile(final Path path) {
        try (BufferedReader in = Files.newBufferedReader(path)) {
            List<String> lines = in.lines().collect(Collectors.toList());
            int n = Integer.parseInt(lines.get(0));
            double[] diagonal = makeVector(lines.get(1), n);
            double[] rowProfile = makeVector(lines.get(2), n);
            double[] columnProfile = makeVector(lines.get(3), n);
            double[] indexesD = makeVector(lines.get(4), n);
            int[] indexes = new int[n];
            for (int i = 0; i < n; i++) {
                indexes[i] = (int) indexesD[i];
            }
            double[] b = makeVector(lines.get(5), n);

            return new LUProfileMatrix(n, diagonal, rowProfile, columnProfile, indexes, b);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("Cannot access file: %s%nException thrown: %s", path, e.getMessage())
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid input file format: %s", e.getMessage())
            );
        }
    }

    private static double[] makeVector(final String line, final int n) {
        CPPDoubleVector vector = new CPPDoubleVector(n);
        for (double v : getDoubles(line)) {
            vector.add(v);
        }
        return vector.toArray();
    }

    private static double[] getDoubles(final String line) {
        return Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }

    private static double[] getBVector(final List<String> lines, final int n) {
        double[] b = new double[n];
        int ind = 0;
        for (String bi : lines.get(n + 1).split(" ")) {
            b[ind++] = Double.parseDouble(bi);
        }
        return b;
    }

    @Override
    public double[] solve() {
        checkUpgraded();

        if (status == 2) {
            throw new SolutionException(SolutionType.MULTIPLE);
        }
        if (status == 1) {
            throw new SolutionException(SolutionType.NO);
        }

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            y[i] = b[i];
            for (int j = 0; j < i; j++) {
                y[i] -= y[j] * getL(i, j);
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = y[i];
            for (int j = i + 1; j < n; j++) {
                x[i] -= x[j] * getU(i, j);
            }
            x[i] /= getU(i, i);
        }
        return x;
    }

    @Override
    public void upgrade() {
        checkNotUpgraded();

        for (int k = 0, p_k = 0; k < n; p_k++, k++) {
            for (int j = k; j < n; j++) {
                for (int p = 0, p_col = 0; p < k; p_col++, p++) {
                    double a = get(p_k, p), b = get(p_col, j);
                    if (!QuickMath.isZero(a) && !QuickMath.isZero(b)) {
                        set(p_k, j, get(p_k, j) - a * b);
                    }
                }
            }
            if (QuickMath.isZero(get(p_k, k))) {
                status = 1;
                isLU = true;
                return;
            }
            for (int i = k + 1, p_row = p_k + 1; i < n; p_row++, i++) {
                for (int p = 0, p_col = 0; p < k; p_col++, p++) {
                    double a = get(p_row, p), b = get(p_col, k);
                    if (!QuickMath.isZero(a) && !QuickMath.isZero(b)) {
                        set(p_row, k, get(p_row, k) - a * b);
                    }
                }
                double c = get(p_row, k);
                double d = get(p_k, k);
                if (!QuickMath.isZero(c) && !QuickMath.isZero(d)) {
                    set(p_row, k, c / d);
                }
            }
        }

        isLU = true;
    }

    @Override
    public boolean isUpgraded() {
        return isLU;
    }

    private int getShift(int a) {
        return a - (indexes[a + 1] - indexes[a]);
    }

    private int getIndexInProfile(int shift, int a, int b) {
        return indexes[a] + b - shift;
    }

    private int getIndexInProfile(int a, int b) {
        return getIndexInProfile(getShift(a), a, b);
    }

    public double getU(int i, int j) {
        checkUpgraded();
        checkBounds(i, j);
        return i == j ? diagonal[i] : i > j ? 0 : getByIndexInLU(columnProfile, j, i);
    }

    public double getL(int i, int j) {
        checkUpgraded();
        checkBounds(i, j);
        return i == j ? 1 : i < j ? 0 : getByIndexInLU(rowProfile, i, j);
    }

    private double getByIndexInLU(final double[] profile, int a, int b) {
        int shift = getShift(a);
        return b < shift ? 0 : profile[getIndexInProfile(shift, a, b)];
    }

    public double[][] getRecovered() {
        checkUpgraded();

        double[][] matrix = new double[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new double[n];
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    matrix[i][j] += getL(i, k) * getU(k, j);
                }
            }
        }

        return matrix;
    }

    public double[] getB() {
        return b;
    }

    @Override
    public LUProfileMatrix clone() {
        return new LUProfileMatrix(
                n,
                Arrays.copyOf(diagonal, diagonal.length),
                Arrays.copyOf(rowProfile, rowProfile.length),
                Arrays.copyOf(columnProfile, columnProfile.length),
                indexes,
                b
        );
    }
}
