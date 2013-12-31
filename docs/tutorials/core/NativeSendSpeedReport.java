import org.agilewiki.jactor2.core.plant.Plant;
import org.agilewiki.jactor2.core.messages.AsyncRequest;
import org.agilewiki.jactor2.core.reactors.NonBlockingReactor;

public class NativeSendSpeedReport {
    public static void main(final String[] _args) throws Exception {
        final long count = 100000000L;
        Plant plant = new Plant();
        try {
            NonBlockingReactor sharedReactor = new NonBlockingReactor();
            Ponger ponger = new Ponger(sharedReactor);
            Pinger pinger = new Pinger(sharedReactor, ponger);
            AsyncRequest<Void> loopAReq = pinger.loopAReq(count);
            final long before = System.nanoTime();
            loopAReq.call();
            final long after = System.nanoTime();
            final long duration = after - before;
            SpeedReport.print("Native Send Timings", duration, count);
        } finally {
            plant.close();
        }
    }
}
