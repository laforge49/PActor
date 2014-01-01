package org.agilewiki.jactor2.modules;

import org.agilewiki.jactor2.core.blades.NonBlockingBlade;
import org.agilewiki.jactor2.core.util.Closer;
import org.agilewiki.jactor2.modules.impl.FacilityImpl;
import org.agilewiki.jactor2.modules.transactions.properties.PropertiesProcessor;

/**
 * Provides a thread pool for
 * non-blocking and isolation targetReactor. Multiple facilities with independent life cycles
 * are also supported.
 * (A ServiceClosedException may be thrown when messages cross facilities and the target facility is closed.)
 * In addition, the facility maintains a set of AutoClosable objects that are closed
 * when the facility is closed, as well as a table of properties.
 */

public interface Facility extends Closer, NonBlockingBlade {
    FacilityImpl asFacilityImpl();

    void close() throws Exception;

    String getName();

    PropertiesProcessor getPropertiesProcessor();
}
