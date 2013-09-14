package org.agilewiki.jactor2.osgi;

import org.agilewiki.jactor2.core.messages.AsyncRequest;
import org.agilewiki.jactor2.core.messages.AsyncResponseProcessor;
import org.agilewiki.jactor2.core.reactors.Reactor;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

import java.util.Map;

/**
 * Locates (or waits for) a service.
 */
public class LocateService<T> implements ServiceChangeReceiver<T> {

    /**
     * The processing.
     */
    private Reactor reactor;

    /**
     * The service tracker blade.
     */
    private JAServiceTracker<T> tracker;

    /**
     * The responseProcessor for returning the service.
     */
    private AsyncResponseProcessor<T> responseProcessor;

    /**
     * Create a LocateService blade.
     *
     * @param _reactor The blade processing.
     * @param clazz    Class name of the desired service.
     */
    public LocateService(Reactor _reactor, String clazz) throws Exception {
        reactor = _reactor;
        tracker = new JAServiceTracker(reactor, clazz);
    }

    /**
     * Returns a request to locate the service.
     *
     * @return The request.
     */
    public AsyncRequest<T> getReq() {
        return new AsyncRequest<T>(reactor) {
            @Override
            public void processAsyncRequest() throws Exception {
                tracker.start(LocateService.this);
                responseProcessor = this;
            }
        };
    }

    @Override
    public void serviceChange(ServiceEvent _event,
                              Map<ServiceReference, T> _tracked)
            throws Exception {
        if (_tracked.size() > 0 && responseProcessor != null) {
            T service = _tracked.values().iterator().next();
            responseProcessor.processAsyncResponse(service);
            responseProcessor = null;
            tracker.close();
            tracker = null;
        }
    }

    @Override
    public Reactor getReactor() {
        return reactor;
    }
}
