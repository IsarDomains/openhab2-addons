/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.tplinksmarthome.internal.device;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test class for {@link DeviceState} class.
 *
 * @author Hilbrand Bouwkamp - Initial contribution
 */
public class DeviceStateTest {

    /**
     * Tests if creating an empty {@link DeviceState} object correctly initializes the object.
     */
    @Test
    public void testEmpty() {
        DeviceState ds = new DeviceState();
        assertNotNull(ds.getSysinfo());
        assertNotNull(ds.getRealtime());
    }
}
