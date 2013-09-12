package org.agilewiki.jactor2.core;

import org.agilewiki.jactor2.core.processing.IsolationMessageProcessor;
import org.agilewiki.jactor2.core.threading.Facility;

/**
 * An isolation actor processes requests from other actors one at a time,
 * starting a new request only when a result is returned for the previous
 * one.
 * </p>
 * This is just a convenience class, as any actor which uses an isolation message processor
 * is an isolation actor.
 */
public class IsolationActor extends ActorBase {

    /**
     * Create an isolation actor.
     *
     * @param _facility A set of resources, including a thread pool, for use
     *                  by message processors and their actors.
     */
    public IsolationActor(final Facility _facility) throws Exception {
        initialize(new IsolationMessageProcessor(_facility));
    }
}
