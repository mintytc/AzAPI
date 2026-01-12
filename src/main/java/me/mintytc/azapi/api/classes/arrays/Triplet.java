package me.mintytc.azapi.api.classes.arrays;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a tuple of three values.
 *
 * @since 1.0.0-R0.1
 */
@Getter
@Setter
public class Triplet<A, B, C> {

    A first;
    B second;
    C third;

    public Triplet(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Triplet<?, ?, ?>)) return false;
        Triplet<?, ?, ?> t = (Triplet<?, ?, ?>) o;
        return first.equals(t.first) && second.equals(t.second) && third.equals(t.third);
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode() ^ third.hashCode();
    }
}
