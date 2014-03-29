import java.util.concurrent.atomic.AtomicBoolean;

class BasicActor {
    private AtomicBoolean busy;
    
    protected void start() {
        while (!busy.compareAndSet(false, true))
            Thread.yield();
    }
    
    protected void finish() {
        busy.set(false);
    }
}
