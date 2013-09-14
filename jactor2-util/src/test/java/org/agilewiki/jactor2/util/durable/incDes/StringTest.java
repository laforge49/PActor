package org.agilewiki.jactor2.util.durable.incDes;

import junit.framework.TestCase;
import org.agilewiki.jactor2.core.facilities.Facility;
import org.agilewiki.jactor2.util.durable.Durables;

public class StringTest extends TestCase {
    public void test() throws Exception {
        Facility facility = Durables.createFacility();
        try {
            JAString jaString1 = (JAString) Durables.newSerializable(facility, JAString.FACTORY_NAME);
            JAString jaString2 = (JAString) jaString1.copyReq(null).call();
            jaString2.setValueReq("abc").call();
            JAString jaString3 = (JAString) jaString2.copyReq(null).call();

            int sl = jaString1.getSerializedLength();
            assertEquals(4, sl);
            sl = jaString2.getSerializedLength();
            assertEquals(10, sl);
            sl = jaString3.getSerializedLength();
            assertEquals(10, sl);

            assertNull(jaString1.getValueReq().call());
            assertEquals("abc", jaString2.getValueReq().call());
            assertEquals("abc", jaString3.getValueReq().call());

            Box box = (Box) Durables.newSerializable(facility, Box.FACTORY_NAME);
            box.setValueReq(JAString.FACTORY_NAME).call();
            JAString rpa = (JAString) box.resolvePathnameReq("0").call();
            assertNull(rpa.getValueReq().call());
            assertTrue(rpa.makeValueReq("").call());
            assertFalse(rpa.makeValueReq("Hello?").call());
            rpa = (JAString) (JAString) box.resolvePathnameReq("0").call();
            assertEquals("", rpa.getValueReq().call());
            rpa.setValueReq("bye").call();
            assertEquals("bye", rpa.getValueReq().call());
            sl = rpa.getSerializedLength();
            assertEquals(10, sl);
            rpa.clearReq().call();
            assertNull(rpa.getValueReq().call());

        } finally {
            facility.close();
        }
    }
}
