package org.contact.management.system.exception;

/**
 * Thrown exception when any connectivity issues from MySql
 */
public class DatabaseDependencyException extends Exception {
    public DatabaseDependencyException(final String message) {
        super(message);
    }

    public DatabaseDependencyException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
