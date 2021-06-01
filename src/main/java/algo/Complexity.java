package algo;

public class Complexity {
    private final int n;
    private int complexity;
    private int itr;
    private int innerItr;

    public Complexity(final int n) {
        this.complexity = 0;
        this.n = n;
    }

    public void incItr() {
        this.itr++;
    }

    public void incInnerItr() {
        this.innerItr++;
    }

    public void addItr(final int a) {
        this.itr += a;
    }

    public void addInnerItr(final int a) {
        this.innerItr += a;
    }

    public void addLinearComplexity() {
        addLinearComplexity(1);
    }

    public void addQuadraticComplexity() {
        addQuadraticComplexity(1);
    }

    public void addCubicComplexity() {
        addCubicComplexity(1);
    }

    public void addLinearComplexity(final int times) {
        this.complexity += this.n * times;
    }

    public void addQuadraticComplexity(final int times) {
        this.complexity += this.n * this.n * times;
    }

    public void addCubicComplexity(final int times) {
        this.complexity += this.n * this.n * this.n * times;
    }

    public int getComplexity() {
        return complexity;
    }

    public int getItr() {
        return itr;
    }

    public int getInnerItr() {
        return innerItr;
    }
}
