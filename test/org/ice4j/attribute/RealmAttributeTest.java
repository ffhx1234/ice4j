/*
 * Ice4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.ice4j.attribute;

import junit.framework.*;

import java.util.*;

import org.ice4j.*;
import org.ice4j.attribute.*;

/**
 * Tests the realm attribute class.
 * <p>Organization Network Research Team, Louis Pasteur University</p>
 * @author Emil Ivov
 * @author Sebastien Vincent
 */
public class RealmAttributeTest extends TestCase
{
    private RealmAttribute realmAttribute = null;
    MsgFixture msgFixture = null;
    String realmValue = "domain.org";
    byte[] attributeBinValue = new byte[]{
            (byte)(RealmAttribute.REALM>>8),
            (byte)(RealmAttribute.REALM & 0x00FF),
            0, (byte)realmValue.length(),
            'd', 'o', 'm', 'a', 'i', 'n', '.', 'o', 'r', 'g', 0x00, 0x00};

    protected void setUp() throws Exception
    {
        super.setUp();
        msgFixture = new MsgFixture();

        realmAttribute = new RealmAttribute();
        realmAttribute.setRealm(realmValue.getBytes());

        msgFixture.setUp();
    }

    protected void tearDown() throws Exception
    {
        realmAttribute = null;
        msgFixture.tearDown();

        msgFixture = null;
        super.tearDown();
    }

    /**
     * Tests decoding of the realm attribute.
     * @throws StunException upon a failure
     */
    public void testDecodeAttributeBody() throws StunException
    {
        char offset = 0;
        RealmAttribute decoded = new RealmAttribute();
        char length = (char)realmValue.length();
        decoded.decodeAttributeBody(realmValue.getBytes(), offset, length);

        //realm value
        assertEquals( "decode failed", realmAttribute, decoded);
    }

    /**
     * Tests the encode method
     */
    public void testEncode()
    {
        assertTrue("encode failed",
                   Arrays.equals(realmAttribute.encode(),
                                 attributeBinValue));
    }

    /**
     * Test Equals
     */
    public void testEquals()
    {
        RealmAttribute realmAttribute2 = new RealmAttribute();
        realmAttribute2.setRealm(realmValue.getBytes());

        //test positive equals
        assertEquals("testequals failed", realmAttribute, realmAttribute2);

        //test negative equals
        realmAttribute2 = new RealmAttribute();
        realmAttribute2.setRealm("some other realm".getBytes());

        //test positive equals
        assertFalse("testequals failed",
                    realmAttribute.equals(realmAttribute2));

        //test null equals
        assertFalse("testequals failed",
                    realmAttribute.equals(null));
    }

    /**
     * Tests extracting data length
     */
    public void testGetDataLength()
    {
        char expectedReturn = (char)realmValue.length();
        char actualReturn = realmAttribute.getDataLength();
        assertEquals("getDataLength - failed", expectedReturn, actualReturn);
    }

    /**
     * Tests getting the name
     */
    public void testGetName()
    {
        String expectedReturn = "REALM";
        String actualReturn = realmAttribute.getName();
        assertEquals("getting name failed", expectedReturn, actualReturn);
    }

    public void testSetGetRealm()
    {
        byte[] expectedReturn = realmValue.getBytes();

        RealmAttribute att = new RealmAttribute();
        att.setRealm(expectedReturn);

        byte[] actualReturn = att.getRealm();
        assertTrue("realm setter or getter failed",
                     Arrays.equals( expectedReturn,
                                    actualReturn));
    }
}
