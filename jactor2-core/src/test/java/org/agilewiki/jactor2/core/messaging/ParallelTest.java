package org.agilewiki.jactor2.core.messaging;

import junit.framework.TestCase;
import org.agilewiki.jactor2.core.misc.Delay;
import org.agilewiki.jactor2.core.processing.MessageProcessor;
import org.agilewiki.jactor2.core.processing.NonBlockingMessageProcessor;
import org.agilewiki.jactor2.core.threading.Facility;

/**
 * Test code.
 */
public class ParallelTest extends TestCase {
    private static final int LOADS = 10;
    private static final long DELAY = 200;

    private MessageProcessor messageProcessor;
    private Facility facility;
    private AsyncRequest<Void> start;

    public void test() throws Exception {
        facility = new Facility();
        messageProcessor = new NonBlockingMessageProcessor(facility);

        start = new AsyncRequest<Void>(messageProcessor) {
            AsyncRequest<Void> dis = this;

            @Override
            public void processAsyncRequest()
                    throws Exception {
                final ResponseCounter<Void> responseCounter = new ResponseCounter<Void>(
                        LOADS, null, dis);
                int i = 0;
                while (i < LOADS) {
                    final Delay dly = new Delay(facility);
                    dly.sleepSReq(ParallelTest.DELAY).send(messageProcessor,
                            responseCounter);
                    i += 1;
                }
            }
        };

        final long t0 = System.currentTimeMillis();
        start.call();
        final long t1 = System.currentTimeMillis();
        assertTrue((t1 - t0) < DELAY + DELAY / 2);
        facility.close();
    }
}
