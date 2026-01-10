package me.mintytc.azapi.core.lifecycle;

/**
 * Represents an object that can be disposed/cleaned up.
 * Typically used for temporary resources like listeners, tasks, or caches.
 *
 * @since 1.0.0-R0.1-BASE
 */
public interface Disposable {

    /**
     * Dispose of this object, freeing any resources it holds.
     * Should be idempotent (safe to call multiple times).
     */
    void dispose();

    /**
     * Whether this object has already been disposed.
     * Default implementation returns false.
     *
     * @return true if disposed, false otherwise
     */
    default boolean isDisposed() {
        return false;
    }
}
