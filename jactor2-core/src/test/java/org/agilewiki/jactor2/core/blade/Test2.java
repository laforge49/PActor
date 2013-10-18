package org.agilewiki.jactor2.core.blade;

import junit.framework.TestCase;
import org.agilewiki.jactor2.core.facilities.Plant;
import org.agilewiki.jactor2.core.reactors.IsolationReactor;
import org.agilewiki.jactor2.core.reactors.NonBlockingReactor;
import org.agilewiki.jactor2.core.reactors.Reactor;

/**
 * Test code.
 */
public class Test2 extends TestCase {
    public void testI() throws Exception {
        System.out.println("testI");
        final Plant plant = new Plant();
        final Reactor reactor = new NonBlockingReactor(plant);
        final BladeA bladeA = new BladeA(reactor);
        final BladeB bladeB = new BladeB(reactor);
        try {
            bladeB.throwRequest(bladeA).call();
        } catch (final SecurityException se) {
            plant.close();
            return;
        }
        plant.close();
        throw new Exception("Security exception was not caught");
    }

    public void testIII() throws Exception {
        System.out.println("testIII");
        final Plant plant = new Plant();
        final BladeA bladeA = new BladeA(new IsolationReactor(plant));
        final BladeB bladeB = new BladeB(new IsolationReactor(plant));
        try {
            bladeB.throwRequest(bladeA).call();
        } catch (final SecurityException se) {
            plant.close();
            return;
        }
        plant.close();
        throw new Exception("Security exception was not caught");
    }
}
