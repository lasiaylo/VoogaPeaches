package util.math.num;

import util.math.interfaces.IVector;

import java.util.Arrays;

public class Vector implements IVector<Vector, Double> {
    private int d;

    private double[] arr;

    public double x;
    public double y;

    public Vector(double... a) {
        if (a.length < 2)
            throw new IllegalArgumentException();
        arr = a;
        d = a.length;
        x = a[0];
        y = a[0];
    }

    public Vector(Vector p) {
        arr = new double[p.d];
        for (int i = 0; i < p.d; ++i)
            arr[i] = p.at(i);
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }

    @Override
    public int length() {
        return arr.length;
    }

    @Override
    public Vector add(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        double[] _arr = new double[d];
        for (int i = 0; i < d; ++i)
            _arr[i] = at(i) + p.at(i);
        return new Vector(_arr);
    }

    @Override
    public Vector multiply(Double s) {
        double[] _arr = new double[d];
        for (int i = 0; i < d; ++i) {
            _arr[i] = s * at(i);
        }
        return new Vector(_arr);
    }

    @Override
    public Vector subtract(Vector p) {
        return add(p.negate());
    }

    @Override
    public Vector negate() {
        return multiply(-1.);
    }

    @Override
    public Vector translate(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        for (int i = 0; i < d; ++i)
            arr[i] += p.at(i);
        return this;
    }

    @Override
    public Vector scale(Double s) {
        for (int i = 0; i < d; ++i)
            arr[i] += s;
        return this;
    }

    @Override
    public Double at(int i) {
        return arr[i];
    }

    @Override
    public void at(int i, Double v) {
        arr[i] = v;
    }

    @Override
    public Double norm() {
        return norm(2);
    }

    @Override
    public Double norm(int n) {
        double sum = 0;
        for (int i = 0; i < d; ++i)
            sum += Math.pow(at(i), n);
        return Math.pow(sum, 1 / n);
    }

    @Override
    public Vector normalize() {
        return multiply(1. / this.norm());
    }

    @Override
    public Double dot(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        double sum = 0;
        for (int i = 0; i < d; ++i)
            sum += at(i) * p.at(i);
        return sum;
    }

    @Override
    public Vector cross(Vector p) {
        if (d != p.d || d != 3)
            throw new IllegalArgumentException();
        return new Vector(at(2) * p.at(3) - at(3) * p.at(2),
                at(3) * p.at(1) - at(1) * p.at(3),
                at(1) * p.at(2) - at(2) * p.at(1)
        );
    }
}
