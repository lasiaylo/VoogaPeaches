package util.math.interfaces;

public interface IMatrix<Self, Primary> {

    /**
     * String representation of a matrix
     *
     * @return string
     */
    String toString();

    /**
     * Add two matrices
     *
     * @param m: other matrix
     * @return new matrix (this + other)
     */
    Self add(Self m);

    /**
     * Multiply two matrices
     *
     * @param m: other matrix
     * @return new matrix (this * other)
     */
    Self multiply(Self m);

    /**
     * Negate a matrix
     *
     * @return new matrix (-this)
     */
    Self negate();

    /**
     * Subtract two matrices
     *
     * @param m: other matrix
     * @return new matrix (this - other)
     */
    Self subtract(Self m);

    /**
     * Get the inverse of a matrix
     *
     * @return new matrix (this ^ (-1))
     */
    Self inverse();

    /**
     * Get the transpose of a matrix
     *
     * @return new matrix (this ^ (T))
     */
    Self transpose();

    /**
     * Get the determinant
     *
     * @return det(this)
     */
    Primary det();

    /**
     * Get a row
     *
     * @param i: row number
     * @return new vector representing i-th row
     */
    IVector row(int i);

    /**
     * Get a column
     *
     * @param i: column number
     * @return new vector representing i-th column
     */
    IVector col(int i);

    /**
     * Get element
     *
     * @param i: element number
     * @return element at position i
     */
    Primary at(int i);

    /**
     * Get element
     *
     * @param i: row number
     * @param j: column number
     * @return element at position i, j
     */
    Primary at(int i, int j);

    /**
     * Get element
     *
     * @param i: row number
     * @param j: column number
     * @param v: value
     */
    void at(int i, int j, Primary v);
}
