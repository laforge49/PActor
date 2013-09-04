package org.agilewiki.jactor2.core.messaging;

import org.agilewiki.jactor2.core.processing.IsolationMessageProcessor;
import org.agilewiki.jactor2.core.threading.ModuleContext;
import org.agilewiki.jactor2.core.processing.MessageProcessor;

/**
 * Test code.
 */
public class Actor3 {
    private final MessageProcessor messageProcessor;
    public final Request<Void> hi3;

    public Actor3(final ModuleContext _context) {
        this.messageProcessor = new IsolationMessageProcessor(_context);

        hi3 = new Request<Void>(messageProcessor) {
            @Override
            public void processRequest(
                    final Transport<Void> responseProcessor)
                    throws Exception {
                System.out.println("Hello world!");
                responseProcessor.processResponse(null);
            }
        };
    }
}
