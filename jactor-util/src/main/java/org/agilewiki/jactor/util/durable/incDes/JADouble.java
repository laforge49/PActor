package org.agilewiki.jactor.util.durable.incDes;

/**
 * A durable double.
 */
public interface JADouble extends FLenS<Double> {

    /**
     * Size of a serialized JADouble in bytes.
     */
    public final static int LENGTH = 8;

    /**
     * Factory name for JADouble.
     */
    public static final String FACTORY_NAME = "double";
}
