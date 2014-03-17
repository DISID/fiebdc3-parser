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

package com.disid.fiebdc3.antlr4;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.disid.fiebdc3.Database;

/**
 * Integration tests for the {@link AntlrFiebdc3Service} class.
 * 
 * @author DiSiD Team
 * 
 */
public class AntlrFiebdc3ServiceIT {

    private AntlrFiebdc3Service service;

    @Before
    public void setUp() throws Exception {
        service = new AntlrFiebdc3Service();
    }

    /**
     * Test method for
     * {@link com.disid.fiebdc3.antlr4.AntlrFiebdc3Service#parse(java.io.Reader)}
     * .
     */
    @Test
    public void testParse() throws Exception {
        URL testResource = this.getClass().getClassLoader()
                .getResource("test.bc3");
        File testFile = new File(testResource.getPath());
        // create a CharStream that reads from standard input
        Reader testReader = new FileReader(testFile);
        
        Database database = service.parse(testReader);
        
        assertFalse(database.hasOrphanedConcepts());

        // System.out.println("Parsed file: " + database);
    }

}
