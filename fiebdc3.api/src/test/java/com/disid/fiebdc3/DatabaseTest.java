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

import static org.junit.Assert.*;

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
        Concept testConcept = database.getConcept("test");
        assertNull(testConcept);

        testConcept = database.getOrAddConcept("test");

        assertNotNull(testConcept);
        assertEquals("test", testConcept.getCode());

        testConcept = database.getConcept("test");
        assertNotNull(testConcept);

        ConceptBreakdown bd = database.addConceptBreakdown("test");
        assertEquals(testConcept, database.getConcept(bd));
    }

    @Test
    public void testAddConceptBreakdown() {
        // Add an orphan ConceptBreakdown
        ConceptBreakdown bd = database.addConceptBreakdown("test");
        assertEquals(null, bd.getParentConceptCode());
        assertEquals("test", bd.getConceptCode());
        assertTrue(database.hasOrphanedConceptBreakdowns());

        // Add the orphan Breakdown to a parent one
        ConceptBreakdown bd3 = database.addConceptBreakdown("parent", "test");
        assertEquals("parent", bd3.getParentConceptCode());
        assertEquals("test", bd3.getConceptCode());
        assertTrue(database.hasOrphanedConceptBreakdowns());
        database.setRoot("parent");
        assertFalse(database.hasOrphanedConceptBreakdowns());

        // Add another child to the root ConceptBreakdown
        ConceptBreakdown bd2 = database.addConceptBreakdown("parent", "child");
        assertEquals("parent", bd2.getParentConceptCode());
        assertEquals("child", bd2.getConceptCode());
        assertFalse(database.hasOrphanedConceptBreakdowns());
    }

    @Test
    public void testAddMeasurement() {
        Measurement measurement = database.createMeasurement();
        assertTrue(database.hasOrphanedMeasurements());

        database.setMeasurement("test", measurement);
        assertFalse(database.hasOrphanedMeasurements());

        ConceptBreakdown bd = database.addConceptBreakdown("test");
        assertEquals(measurement, bd.getMeasurement());
    }

}
