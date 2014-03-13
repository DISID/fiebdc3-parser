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
 * Unit tests for the {@link Concept} class.
 * 
 * @author DiSiD Team
 */
public class ConceptTest {

    private Concept concept;
    private Concept childConcept;
    private Concept grandChildConcept;

    @Before
    public void setUp() throws Exception {
        concept = new Concept("test");

        childConcept = new Concept("child");
        concept.addChildConcept(childConcept);

        grandChildConcept = new Concept("grandchild");
        childConcept.addChildConcept(grandChildConcept);
    }

    @Test
    public void testAddChildConcept() {

        boolean childFound = false;
        for (Concept child : concept.getChildConcepts()) {
            childFound = child == childConcept;
        }
        assertTrue(childFound);
    }

    @Test
    public void testGetConceptSimple() {
        // Direct concept code search
        assertEquals(concept, concept.getConcept("test"));
        assertEquals(childConcept, concept.getConcept("child"));
        assertEquals(grandChildConcept, concept.getConcept("grandchild"));
    }

    @Test
    public void testGetConceptWithPath() {
        // Concept code with parents path
        assertEquals(childConcept, concept.getConcept("test\\child"));
        assertEquals(grandChildConcept,
                concept.getConcept("test\\child\\grandchild"));
    }

    @Test
    public void testGetConceptWithSymbols() {
        // Concept code with #'s at the end
        assertEquals(concept, concept.getConcept("test##"));
        assertEquals(childConcept, concept.getConcept("child#"));
    }

    @Test
    public void testGetConceptWithPathAndSymbols() {
        // Mixed tests
        assertEquals(childConcept, concept.getConcept("test##\\child#"));
        assertEquals(grandChildConcept,
                concept.getConcept("test##\\child#\\grandchild"));
    }
}
