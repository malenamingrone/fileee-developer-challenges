package com.fileee.utils;

import org.junit.Test;

import static com.fileee.utils.StringUtils.*;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testPairStar() {
        assertEquals("fo*oBar", pairStar("fooBar", 0));
        assertEquals("a*a*ah*hj*j*j", pairStar("aaahhjjj", 0));
        assertEquals("bo*ok*ke*eper", pairStar("bookkeeper", 0));
        assertEquals("file*e*e", pairStar("fileee", 0));
    }

    @Test
    public void testEmailAddressValidation() {
        assertFalse(isValidEmailAddress("@."));
        assertFalse(isValidEmailAddress("@foo.com"));
        assertFalse(isValidEmailAddress("foo.bar@com"));
        assertTrue(isValidEmailAddress("foo.bar@fileee.com"));
    }

}
