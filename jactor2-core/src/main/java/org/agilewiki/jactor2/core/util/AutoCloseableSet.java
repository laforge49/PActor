/*
 * Copyright (C) 2013 Sebastien Diot.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.agilewiki.jactor2.core.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.agilewiki.jactor2.core.facilities.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A (non-thread-safe) set of AutoCloseable resources.
 *
 * @author monster
 */
public class AutoCloseableSet implements Iterable<AutoCloseable>, AutoCloseable {

    /*
     * Should we log errors in calls to close()?
     *  Generally not. Wierdness will often occur when closing. It should be robust and graceful.
     *  The problem is that close exceptions are often red herings. They are errors caused by other errors,
     *  and they typically occur after the original error was detected.
     */
    private static volatile boolean LOG_CLOSE_ERRORS = Plant.DEBUG;

    /** The logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AutoCloseableSet.class);

    /** Dummy value */
    private static final Object VALUE = new Object();

    /**
     * A hash set of AutoCloseable objects.
     * Can only be accessed via a request to the facility.
     */
    protected final Map<AutoCloseable, Object> closeables = new WeakHashMap<AutoCloseable, Object>();

    /** Disable close() error logging. */
    public static void disableCloseErrorLogging() {
        LOG_CLOSE_ERRORS = false;
    }

    /** Enable close() error logging. */
    public static void enableCloseErrorLogging() {
        LOG_CLOSE_ERRORS = true;
    }

    /**
     * Adds an AutoCloseable resource.
     *
     * @param resource element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     * @throws java.lang.NullPointerException if resource is null
     */
    public boolean add(final AutoCloseable resource) {
        Objects.requireNonNull(resource, "resource");
        return closeables.put(resource, VALUE) == null;
    }

    /**
     * Removes an AutoCloseable resource.
     *
     * @param resource object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws java.lang.NullPointerException if resource is null
     */
    public boolean remove(final AutoCloseable resource) {
        Objects.requireNonNull(resource, "resource");
        return closeables.remove(resource) != null;
    }

    /** Iterates over the resources. */
    @Override
    public Iterator<AutoCloseable> iterator() {
        return closeables.keySet().iterator();
    }

    /**
     * Closes all resources.
     * Swallows all errors.
     */
    @Override
    public void close() {
        final AutoCloseable[] array = closeables.keySet().toArray(
                new AutoCloseable[closeables.size()]);
        closeables.clear();
        for (final AutoCloseable ac : array) {
            try {
                ac.close();
            } catch (final Throwable t) {
                if (ac != null && LOG_CLOSE_ERRORS) {
                    LOG.warn("Error closing a " + ac.getClass().getName(), t);
                }
            }
        }
    }
}