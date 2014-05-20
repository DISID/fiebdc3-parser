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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ConceptBreakdown} class.
 * 
 * @author DiSiD Team
 */
public class ConceptBreakdownTest {

    private ConceptBreakdown root;
    private ConceptBreakdown child;
    private ConceptBreakdown grandchild;

    @Before
    public void setUp() throws Exception {
        root = new ConceptBreakdown(null, "root");

        child = new ConceptBreakdown("root", "child");
        root.addChildBreakdownInfo(child);

        grandchild = new ConceptBreakdown("child", "grandchild");
        child.addChildBreakdownInfo(grandchild);
    }

    @Test
    public void testConceptCodes() {
        // Concept codes
        assertEquals(null, root.getParentConceptCode());
        assertEquals("root", root.getConceptCode());
        assertEquals("root", child.getParentConceptCode());
        assertEquals("child", child.getConceptCode());

        // Cleaned concept codes
        root = new ConceptBreakdown("parent##", "test#");
        assertEquals("parent", root.getParentConceptCode());
        assertEquals("test", root.getConceptCode());
    }

    @Test
    public void testIsMyConceptCode() {
        // Check concept code equality
        assertTrue(root.isMyConceptCode("root"));
        assertTrue(root.isMyConceptCode("root#"));
        assertTrue(root.isMyConceptCode("root##"));

        assertFalse(root.isMyConceptCode("parent"));
    }

    @Test
    public void testAddChildChapter() {

        boolean childFound = false;
        for (ConceptBreakdown childBreakdown : root.getChildBreakdownInfos()) {
            if (childBreakdown.equals(child)) {
                childFound = true;
            }
        }
        assertTrue(childFound);
    }

    @Test
    public void testGetSubchapter() {
        // Concept code with parents path
        assertEquals(child, root.getCodeBreakdown("root\\child"));
        assertEquals(grandchild,
                root.getCodeBreakdown("root\\child\\grandchild"));
    }

    @Test
    public void testGetChapterFromConceptWithSymbols() {
        // Concept code with #'s at the end
        assertEquals(root, root.getCodeBreakdown("root##"));
        assertEquals(child, root.getCodeBreakdown("child#"));
        assertEquals(grandchild, root.getCodeBreakdown("grandchild#"));
    }

    @Test
    public void testGetChapterFromConceptWithPathAndSymbols() {
        // Mixed tests
        assertEquals(child, root.getCodeBreakdown("root##\\child#"));
        assertEquals(grandchild,
                root.getCodeBreakdown("root##\\child#\\grandchild"));

        assertNull(root.getCodeBreakdown("root##\\nochapter"));
    }

}
