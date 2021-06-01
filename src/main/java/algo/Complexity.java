package algo;

public class Complexity {
    private final int n;
    private int complexity;
    private int itr;
    private int innerItr;
    private int hessianCalls;
    private int gradCalls;
    private int fCalls;

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

    public void addComplexity(final int l, final int q, final int c) {
        addLinearComplexity(l);
        addQuadraticComplexity(q);
        addCubicComplexity(c);
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

    public int getHessianCalls() {
        return hessianCalls;
    }

    public int getGradCalls() {
        return gradCalls;
    }

    public int getFCalls() {
        return fCalls;
    }

    public void fCallsInc() {
        this.fCalls++;
    }

    public void gradCallsInc() {
        this.gradCalls++;
    }

    public void hessianCallsInc() {
        this.hessianCalls++;
    }

    public void addFCalls(final int n) {
        this.fCalls += n;
    }

    public void addGradCalls(final int n) {
        this.gradCalls += n;
    }

    public void addHessianCalls(final int n) {
        this.hessianCalls += n;
    }

    public void addCalls(final int f, final int g, final int h) {
        addFCalls(f);
        addGradCalls(g);
        addHessianCalls(h);
    }
}
