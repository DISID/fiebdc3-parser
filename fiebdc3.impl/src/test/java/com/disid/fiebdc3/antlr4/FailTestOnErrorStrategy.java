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

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 * Detect if there has been any parse errors, so the tests fail.
 * 
 * @author DiSiD Team
 */
public class FailTestOnErrorStrategy extends DefaultErrorStrategy {
    
    private boolean hasErrors = false;

    @Override
    public void reportError(Parser recognizer, RecognitionException e) {
	hasErrors = true;
        super.reportError(recognizer, e);
    }
    
    @Override
    public void recover(Parser recognizer, RecognitionException e) {
	hasErrors = true;
        super.recover(recognizer, e);
    }
    
    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
	hasErrors = true;
        return super.recoverInline(recognizer);
    }
    
    public boolean isHasErrors() {
	return hasErrors;
    }
}