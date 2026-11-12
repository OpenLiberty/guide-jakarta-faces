// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.openliberty.guides.bean.model.SystemLoadData;

public class SystemLoadBeanTest {

    private SystemLoadBean systemLoadBean;

    // tag::BeforeEach[]
    @BeforeEach
    // end::BeforeEach[]
    // tag::setUp[]
    public void setUp() {
        systemLoadBean = new SystemLoadBean();
        systemLoadBean.init();
    }
    // end::setUp[]

    @Test
    // tag::testInitMethod[]
    public void testInitMethod() {
        assertNotNull(systemLoadBean.getSystemLoads(),
                      "System loads should not be null after initialization");
        assertFalse(systemLoadBean.getSystemLoads().isEmpty(),
                    "System loads should not be empty after initialization");
    }
    // end::testInitMethod[]

    @Test
    // tag::testFetchSystemLoad[]
    public void testFetchSystemLoad() {
        int initialSize = systemLoadBean.getSystemLoads().size();
        systemLoadBean.fetchSystemLoad();
        int newSize = systemLoadBean.getSystemLoads().size();
        assertEquals(initialSize + 1, newSize,
                     "System loads size should increase by 1 after fetching new data");
    }
    // end::testFetchSystemLoad[]

    @Test
    // tag::testDataIntegrity[]
    public void testDataIntegrity() {
        systemLoadBean.fetchSystemLoad();
        SystemLoadData data = systemLoadBean.getSystemLoads().getFirst();
        assertNotNull(data.getTime(), "Time should not be null");
        assertNotNull(data.getCpuLoad(), "Recent load should not be null");
        assertNotNull(data.getMemoryUsage(), "Memory usage should not be null");
    }
    // end::testDataIntegrity[]
}
