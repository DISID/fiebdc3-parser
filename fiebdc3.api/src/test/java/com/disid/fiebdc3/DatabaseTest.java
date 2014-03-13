/*
 * FIEBDC 3 parser  
 * Copyright (C) 2014 DiSiD Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/copyleft/gpl.html>.
 */
package com.disid.fiebdc3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Database} class.
 * @author DiSiD Team
 */
public class DatabaseTest {

    private Database database;

    @Before
    public void setUp() throws Exception {
       database = new Database();
    }

    @Test
    public void testGetConcept() {
        TestConcept testConcept = new TestConcept("test");
        database.setRootConcept(testConcept);
        
        Concept concept = database.getConcept("test");
        
        assertTrue(testConcept.conceptCalled);
        assertEquals(testConcept, concept);
    }

    private class TestConcept extends Concept {

        boolean conceptCalled = false;
        
        TestConcept(String code) {
            super(code);
        }

        @Override
        public Concept getConcept(String code) {
            conceptCalled = true;
            return this;
        }
    }
}
