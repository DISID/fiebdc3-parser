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

package com.disid.fiebdc3.impl.antlr4;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for the FIEBDC 3 file format ANTLR4 based parser.
 * 
 * @author DiSiD Team
 */
public class Fiebdc3ParserIT {

    private Fiebdc3Parser parser;
    private FailTestOnErrorStrategy errorStrategy;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	URL testResource = this.getClass().getClassLoader()
		.getResource("test.bc3");
	File testFile = new File(testResource.getPath());
	// create a CharStream that reads from standard input
	ANTLRInputStream input = new ANTLRInputStream(new FileReader(testFile));
	// create a lexer that feeds off of input CharStream
	Fiebdc3Lexer lexer = new Fiebdc3Lexer(input) {
	    @Override
	    public void recover(LexerNoViableAltException e) {
		throw new RuntimeException(e); // Bail out
	    }
	};
	// create a buffer of tokens pulled from the lexer
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	parser = new Fiebdc3Parser(tokens);
	errorStrategy = new FailTestOnErrorStrategy();
	parser.setErrorHandler(errorStrategy);
    }

    @Test
    public void testParser() {
	ParseTree tree = parser.database(); // begin parsing at database rule
	System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	assertFalse("Found parse errors", errorStrategy.isHasErrors());
    }
}
