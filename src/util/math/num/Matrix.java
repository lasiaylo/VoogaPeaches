package util.math.num;

import util.math.interfaces.IMatrix;

public class Matrix implements IMatrix<Matrix, Double> {
    private double[][] m;
    private int cols;
    private int rows;

    public Matrix(Double[][] m) {
        rows = m.length;
        cols = m[0].length;

        this.m = new double[cols][rows];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                this.m[i][j] = m[i][j];
    }

    public Matrix(Vector[] v) {
        rows = v.length;
        cols = v[0].length();

        m = new double[cols][rows];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                this.m[i][j] = v[i].at(j);
    }

    public Vector multiply(Vector p) {
        return null;
    }

    @Override
    public Matrix multiply(Matrix m) {
        return null;
    }
 
    @Override
    public Matrix add(Matrix m) {
        return null;
    }

    @Override
    public Matrix negate() {
        return null;
    }

    @Override
    public Matrix subtract(Matrix m) {
        return null;
    }

    @Override
    public Matrix inverse() {
        return null;
    }

    @Override
    public Matrix transpose() {
        return null;
    }

    @Override
    public Double det() {
        return null;
    }

    @Override
    public Vector row(int i) {
        return null;
    }

    @Override
    public Vector col(int i) {
        return null;
    }

    @Override
    public Double at(int i) {
        return null;
    }

    @Override
    public Double at(int i, int j) {
        return null;
    }

    @Override
    public void at(int i, int j, Double v) {

    }
}
