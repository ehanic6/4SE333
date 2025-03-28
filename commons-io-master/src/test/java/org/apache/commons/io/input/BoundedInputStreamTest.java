/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.input;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * Tests for {@link BoundedInputStream}.
 *
 */
public class BoundedInputStreamTest {

    @Test
    public void testReadSingle() throws Exception {
        BoundedInputStream bounded;
        final byte[] helloWorld = "Hello World".getBytes();
        final byte[] hello      = "Hello".getBytes();

        // limit = length
        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), helloWorld.length);
        for (int i = 0; i < helloWorld.length; i++) {
            assertEquals("limit = length byte[" + i + "]", helloWorld[i], bounded.read());
        }
        assertEquals("limit = length end", -1, bounded.read());

        // limit > length
        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), helloWorld.length + 1);
        for (int i = 0; i < helloWorld.length; i++) {
            assertEquals("limit > length byte[" + i + "]", helloWorld[i], bounded.read());
        }
        assertEquals("limit > length end", -1, bounded.read());

        // limit < length
        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), hello.length);
        for (int i = 0; i < hello.length; i++) {
            assertEquals("limit < length byte[" + i + "]", hello[i], bounded.read());
        }
        assertEquals("limit < length end", -1, bounded.read());
    }
    //Emina Hanic + Added Test Method

    @Test
    public void testReadMaxZero() throws Exception {
        final byte[] Hello = "Hello".getBytes();
        BoundedInputStream bis = new BoundedInputStream(new ByteArrayInputStream(Hello), 0);
        assertEquals(-1, bis.read());

    }

    @Test
    public void testReadArray() throws Exception {

        BoundedInputStream bounded;
        final byte[] helloWorld = "Hello World".getBytes();
        final byte[] hello      = "Hello".getBytes();

        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld));
        compare("limit = -1", helloWorld, IOUtils.toByteArray(bounded));

        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), 0);
        compare("limit = 0", new byte[0], IOUtils.toByteArray(bounded));

        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), helloWorld.length);
        compare("limit = length", helloWorld, IOUtils.toByteArray(bounded));

        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), helloWorld.length + 1);
        compare("limit > length", helloWorld, IOUtils.toByteArray(bounded));

        bounded = new BoundedInputStream(new ByteArrayInputStream(helloWorld), helloWorld.length - 6);
        compare("limit < length", hello, IOUtils.toByteArray(bounded));
    }

    private void compare(final String msg, final byte[] expected, final byte[] actual) {
        assertEquals(msg + " length", expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(msg + " byte[" + i + "]", expected[i], actual[i]);
        }
    }
    //Emina Hanic + Added Test Methods for available()
    @Test
    public void testAvailable() throws Exception {
        final byte[] Hello = "Hello".getBytes();
        BoundedInputStream bis = new BoundedInputStream(new ByteArrayInputStream(Hello), 10);
        assertEquals(Hello.length, bis.available());
    }


    @Test
    public void testAvailablePosCondition() throws Exception {
        final byte[] Hello = "Hello".getBytes();
        BoundedInputStream bis = new BoundedInputStream(new ByteArrayInputStream(Hello), 2);

        assertEquals( Hello.length, bis.available());

        for (int i = 0; i < 2; i++) {
            assertEquals("limit < length byte[" + i + "]", Hello[i], bis.read());
        }

        assertEquals(0, bis.available());


    }
    @Test
    public void testAvailableMaxZero() throws Exception {
        final byte[] Hello = "Hello".getBytes();
        BoundedInputStream bis = new BoundedInputStream(new ByteArrayInputStream(Hello), 0);
        assertEquals(0, bis.available());

    }

}
