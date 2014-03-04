package org.agilewiki.jactor2.core.requests.impl;

import org.agilewiki.jactor2.core.reactors.ReactorClosedException;
import org.agilewiki.jactor2.core.reactors.impl.ReactorImpl;
import org.agilewiki.jactor2.core.requests.AsyncResponseProcessor;
import org.agilewiki.jactor2.core.requests.Request;

/**
 * API for internal request implementations.
 *
 * @param <RESPONSE_TYPE>    The return value type.
 */
public interface RequestImpl<RESPONSE_TYPE> extends AutoCloseable {

    /**
     * Returns the Request implemented by this RequestImpl.
     *
     * @return The Request implemented by this RequestImpl.
     */
    Request asRequest();

    /**
     * Passes this Request together with the AsyncResponseProcessor to the target Reactor.
     * Responses are passed back via the targetReactor of the source blades and processed by the
     * provided AsyncResponseProcessor and any exceptions
     * raised while processing the request are processed by the exception handler active when
     * the doSend method was called.
     *
     * @param _source            The sourceReactor on whose thread this method was invoked and which
     *                           will buffer this Request and subsequently receive the result for
     *                           processing on the same thread.
     * @param _responseProcessor Passed with this request and then returned with the result, the
     *                           AsyncResponseProcessor is used to process the result on the same thread
     *                           that originally invoked this method. If null, then no response is returned.
     */
    void doSend(final ReactorImpl _source,
                          final AsyncResponseProcessor<RESPONSE_TYPE> _responseProcessor);

    /**
     * Returns true when a response has been assigned to the request.
     *
     * @return True when a response has not been assigned to the request.
     */
    boolean isComplete();

    /**
     * Returns true when the request is, directly or indirectly, from an IsolationReactor that awaits a response.
     *
     * @return True whhe request is, directly or indirectly, from an IsolationReactor that awaits a response.
     */
    boolean isIsolated();

    /**
     * Execute the AsyncRequest.processAsyncRequest method
     * of the request held by the message. This method is always called on the
     * target reactor's own thread.
     */
    void eval();

    /**
     * Process the exception on the current thread in the facility of the active reactor.
     *
     * @param _activeReactor The reactor providing the facility for processing the throwable.
     * @param _e             The exception to be processed.
     */
    void processException(final ReactorImpl _activeReactor, final Exception _e);

    @Override
    void close();

    /**
     * Cancel this request.
     */
    void cancel();

    /**
     * Returns true if the request has been canceled.
     *
     * @return True if the request has been canceled.
     * @throws ReactorClosedException when the request has been closed.
     */
    boolean isCanceled() throws ReactorClosedException;

    /**
     * Returns true if the request has been canceled.
     *
     * @return True if the request has been canceled.
     */
    boolean _isCanceled() ;

    /**
     * Returns true when the target reactor is not the request source.
     *
     * @return True when the target reactor is not the request source.
     */
    boolean isForeign();

    /**
     * Returns true when the request does not pass back a result.
     *
     * @return True when the request does not pass back a result.
     */
    boolean isOneWay();

    /**
     * Returns true when the request was passed using the signal method.
     * @return True when the request was passed using the signal method.
     */
    boolean isSignal();

    /**
     * Returns the target ReactorImpl.
     * @return The target ReactorImpl.
     */
    ReactorImpl getTargetReactorImpl();

    /**
     * Returns the RequestSource.
     * @return The RequestSource, or null.
     */
    RequestSource getRequestSource();

    /**
     * A response has been received for a subordinate request.
     * @param request    A subordinate request.
     */
    void responseReceived(RequestImpl request);

    /**
     * A response value from a subordinate request has been processed.
     */
    void responseProcessed();
}
