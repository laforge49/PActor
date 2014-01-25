package org.agilewiki.jactor2.core.impl;

/**
 * Signals a migration of the current thread to another targetReactor.
 * As this exception is never thrown when a message is being processed,
 * the application should never be exposed to it.
 */
public class MigrationException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * The newly active targetReactor.
     */
    public final UnboundReactorImpl reactor;

    /**
     * Create a new MigrationException.
     *
     * @param _reactor The newly active processing.
     */
    public MigrationException(final UnboundReactorImpl _reactor) {
        reactor = _reactor;
    }

    /**
     * Speeds things up by not filling in the stack trace.
     *
     * @return this
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
