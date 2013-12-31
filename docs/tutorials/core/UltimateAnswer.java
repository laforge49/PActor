import org.agilewiki.jactor2.core.blades.NonBlockingBladeBase;
import org.agilewiki.jactor2.core.plant.Plant;
import org.agilewiki.jactor2.core.messages.AsyncRequest;
import org.agilewiki.jactor2.core.messages.AsyncResponseProcessor;
import org.agilewiki.jactor2.core.reactors.NonBlockingReactor;

public class UltimateAnswer extends NonBlockingBladeBase {

    public UltimateAnswer(final NonBlockingReactor _reactor) throws Exception {
        initialize(_reactor);
    }
    
    public AsyncRequest<Void> printAnswerAReq() {
        return new AsyncBladeRequest<Void>() {
            @Override
            public void processAsyncRequest() throws Exception {
                System.out.println("*** 42 ***");
                processAsyncResponse(null);
            }
        };
    }
    
    public static void main(final String[] _args) throws Exception {
        Plant plant = new Plant();
        try {
            UltimateAnswer ultimateAnswer = new UltimateAnswer(new NonBlockingReactor());
            AsyncRequest<Void> printAnswerAReq = ultimateAnswer.printAnswerAReq();
            printAnswerAReq.call();
        } finally {
            plant.close();
        }
    }
}