package org.agilewiki.jactor2.core.messages;

import org.agilewiki.jactor2.core.blades.ExceptionHandler;
import org.agilewiki.jactor2.core.reactors.Reactor;
import org.agilewiki.jactor2.core.reactors.ReactorBase;

abstract public class SyncRequest<RESPONSE_TYPE>
        extends RequestBase<RESPONSE_TYPE> {

    public static <RESPONSE_TYPE> RESPONSE_TYPE local(
            final Reactor _source,
            final SyncRequest<RESPONSE_TYPE> _syncRequest) throws Exception {
        return _syncRequest.local(_source);
    }

    /**
     * Create a SyncRequest.
     *
     * @param _targetReactor The reactor where this SyncRequest Objects is passed for processing.
     *                       The thread owned by this reactor will process this SyncRequest.
     */
    public SyncRequest(Reactor _targetReactor) {
        super(_targetReactor);
    }

    /**
     * The processSyncRequest method will be invoked by the target Reactor on its own thread
     * when the SyncRequest is dequeued from the target inbox for processing.
     *
     * @return The value returned by the target blade.
     */
    abstract protected RESPONSE_TYPE processSyncRequest()
            throws Exception;

    @Override
    protected void processRequestMessage() throws Exception {
        processObjectResponse(processSyncRequest());
    }

    /**
     * Process the request immediately.
     *
     * @param _source The reactor on whose thread this method was invoked and which
     *                must be the same as the reactor of the target.
     * @return The value returned by the target blade.
     */
    public RESPONSE_TYPE local(final Reactor _source) throws Exception {
        use();
        ReactorBase messageProcessor = (ReactorBase) _source;
        if (!messageProcessor.isRunning())
            throw new IllegalStateException(
                    "A valid source reactor can not be idle");
        if (messageProcessor != getMessageProcessor())
            throw new IllegalArgumentException("Reactor is not shared");
        messageSource = messageProcessor;
        oldMessage = messageProcessor.getCurrentMessage();
        sourceExceptionHandler = messageProcessor.getExceptionHandler();
        messageProcessor.setCurrentMessage(this);
        messageProcessor.setExceptionHandler(null);
        try {
            return processSyncRequest();
        } catch (Exception e) {
            ExceptionHandler<RESPONSE_TYPE> currentExceptionHandler = messageProcessor.getExceptionHandler();
            if (currentExceptionHandler == null)
                throw e;
            return currentExceptionHandler.processException(e);
        } finally {
            messageProcessor.setCurrentMessage(oldMessage);
            messageProcessor.setExceptionHandler(sourceExceptionHandler);
        }
    }
}