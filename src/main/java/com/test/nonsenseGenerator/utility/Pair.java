package com.test.nonsenseGenerator.utility;

/**
 * A generic class representing an immutable pair of two values of different types.
 * Each pair stores two elements: first and second. Both elements can be of different types.
 *
 * @param <First> the type of the first element in the pair
 * @param <Second> the type of the second element in the pair
 * @author ElementiIngegneria Team
 * @version 1.0
 */
public class Pair<First, Second> {
    private First first;
    private Second second;

    /**
     * Constructs a new Pair with the specified first and second values.
     * Both parameters are stored internally and can be retrieved using getter methods.
     *
     * @param first the first element to store in the pair
     * @param second the second element to store in the pair
     */
    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of this pair.
     *
     * @return the first element
     */
    public First getFirst() {
        return first;
    }

    /**
     * Returns the second element of this pair.
     *
     * @return the second element
     */
    public Second getSecond() {
        return second;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!first.equals(pair.first)) return false;
        return second.equals(pair.second);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the pair in format "first-second"
     */
    @Override
    public String toString() {
        return first + "-" + second;
    }
}