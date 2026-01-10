package me.mintytc.azapi.core.storage;

import java.io.IOException;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(IOException e) {
        super(e);
    }
}
