package me.mintytc.azapi.core.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class UStream {

    private UStream() {
    }

    /**
     * Collects a Stream into a mutable List.
     *
     * @param stream the stream to collect
     * @param <T>    the element type
     *
     * @return a mutable List containing all stream elements
     * @since 1.0.0-R0.1-BASE
     */
    public static <T> List<T> toList(java.util.stream.Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Collects a Stream into an immutable List.
     *
     * @param stream the stream to collect
     * @param <T>    the element type
     *
     * @return an immutable List containing all stream elements
     * @since 1.0.0-R0.1-BASE
     */
    public static <T> List<T> toUnmodifiableList(java.util.stream.Stream<T> stream) {
        return Collections.unmodifiableList(stream.collect(Collectors.toList()));
    }

    /**
     * Safely collects a Stream into an immutable List, returning an empty list if null.
     *
     * @param stream the stream to collect
     * @param <T>    the element type
     *
     * @return an immutable List or empty list if stream is null
     * @since 1.0.0-R0.1-BASE
     */
    public static <T> List<T> toUnmodifiableListSafe(java.util.stream.Stream<T> stream) {
        if (stream == null) return Collections.emptyList();
        return Collections.unmodifiableList(stream.collect(Collectors.toList()));
    }
}
