package org.agilewiki.jactor2.util;

import junit.framework.TestCase;
import org.agilewiki.jactor2.core.ActorBase;
import org.agilewiki.jactor2.core.DefaultMailboxFactory;

public class PropertiesTest extends TestCase {
    public void test() throws Exception {
        final DefaultMailboxFactory mailboxFactory1 = new DefaultMailboxFactory();
        final DefaultMailboxFactory mailboxFactory2 = new DefaultMailboxFactory();
        try {
            JAProperties p1 = new JAProperties(mailboxFactory1, null);
            JAProperties p2 = new JAProperties(mailboxFactory2, p1);
            p1.putProperty("a", "foo");
            p2.putProperty("b", "bar");
            ActorBase z = new ActorBase();
            z.initialize(mailboxFactory2.createNonBlockingMailbox());
            String a = (String) JAProperties.getProperty(z, "a");
            assertEquals("foo", a);
            String b = (String) JAProperties.getProperty(z, "b");
            assertEquals("bar", b);
            String c = (String) JAProperties.getProperty(z, "c");
            assertNull(c);
        } finally {
            mailboxFactory2.close();
            mailboxFactory1.close();
        }
    }
}
