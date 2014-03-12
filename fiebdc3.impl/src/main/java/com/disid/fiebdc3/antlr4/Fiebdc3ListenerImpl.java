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

import com.disid.fiebdc3.Concept;
import com.disid.fiebdc3.Database;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CConceptContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CDateContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CPriceContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CSummaryContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CTypeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CUnitContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VCertDateContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VCertNumContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VCharsetContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VCommentsContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VFilePropertyContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VGeneratedByContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VHeaderContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VInfoTypeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.VVersionFormatContext;

/**
 * Listener for the Fiebdc3 parser events.
 * @author DiSiD Team
 */
public class Fiebdc3ListenerImpl extends Fiebdc3BaseListener {
    
    private Database database = new Database();
    
    private Concept currentConcept;
    
    ////////////////
    // V register //
    ////////////////
    @Override
    public void enterVCertDate(VCertDateContext ctx) {
        database.setCertDate(ctx.TEXT().getText());
    }
    
    @Override
    public void enterVCertNum(VCertNumContext ctx) {
        database.setCertNum(Integer.valueOf(ctx.TEXT().getText()));
    }
    
    @Override
    public void enterVCharset(VCharsetContext ctx) {
        database.setCharset(ctx.getText());
    }
    
    @Override
    public void enterVComments(VCommentsContext ctx) {
        database.setComments(ctx.TEXT().getText());
    }
    
    @Override
    public void enterVFileProperty(VFilePropertyContext ctx) {
        database.setFileProperty(ctx.TEXT().getText());
    }
    
    @Override
    public void enterVGeneratedBy(VGeneratedByContext ctx) {
        database.setGeneratedBy(ctx.TEXT().getText());
    }
    
    @Override
    public void enterVHeader(VHeaderContext ctx) {
        database.setHeader(ctx.TEXT().getText());
    }
    
    @Override
    public void enterVInfoType(VInfoTypeContext ctx) {
        database.setInfoType(Integer.valueOf(ctx.TEXT().getText()));
    }
    
    @Override
    public void enterVVersionFormat(VVersionFormatContext ctx) {
        database.setFileFormat(ctx.TEXT().getText());
    }
    
    ////////////////
    // C register //
    ////////////////
    
    @Override
    public void enterCConcept(CConceptContext ctx) {
        currentConcept = new Concept();
    }
    
    @Override
    public void enterCCode(CCodeContext ctx) {
        String code = ctx.TEXT().getText();
        currentConcept.setCode(code);
        
        if (code.endsWith("##")) { // Root concept
            database.setRootConcept(currentConcept);
        }
    }
    
    @Override
    public void enterCDate(CDateContext ctx) {
        currentConcept.setLastUpdate(ctx.TEXT().getText());
    }
    
    @Override
    public void enterCPrice(CPriceContext ctx) {
        currentConcept.setPrice(ctx.TEXT().getText());
    }

    @Override
    public void enterCSummary(CSummaryContext ctx) {
        currentConcept.setSummary(ctx.TEXT().getText());
    }
    
    @Override
    public void enterCType(CTypeContext ctx) {
        currentConcept.setType(ctx.TEXT().getText());
    }
    
    @Override
    public void enterCUnit(CUnitContext ctx) {
        currentConcept.setMeasureUnit(ctx.TEXT().getText());
    }
    
    public Database getDatabase() {
        return database;
    }
}
