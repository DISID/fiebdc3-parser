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

import java.io.IOException;
import java.io.Reader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.disid.fiebdc3.Database;
import com.disid.fiebdc3.Fiebdc3Service;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser;

/**
 * Implementation of a Fiebdc 3 file format parser service, based on ANTLR4.
 * 
 * @author DiSiD Team
 */
public class AntlrFiebdc3Service implements Fiebdc3Service {

    @Override
    public Database parse(Reader fiebdcData) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(fiebdcData);
        // create a lexer that feeds off of input CharStream
        Fiebdc3Lexer lexer = new Fiebdc3Lexer(input) {
            @Override
            public void recover(LexerNoViableAltException e) {
                throw new RuntimeException(e); // Bail out
            }
        };
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Fiebdc3Parser parser = new Fiebdc3Parser(tokens);
        ParseTree tree = parser.database(); // begin parsing at database rule

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Walk the tree created during the parse, trigger callbacks
        Fiebdc3ListenerImpl listener = new Fiebdc3ListenerImpl();
        walker.walk(listener, tree);

        return listener.getDatabase();
    }

}
