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
import com.disid.fiebdc3.Measurement;
import com.disid.fiebdc3.Measurement.Line;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CConceptContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CDateContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CPriceContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CSummaryContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CTypeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.CUnitContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.DBreakdownContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.DChildCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.DFactorContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.DParentCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.DPerformanceContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MChildCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MCommentContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MHeightContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MLabelContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MLatitudeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MLineContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MLongitudeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MMeasurementContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MParentCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MPositionContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MTotalMeasurementContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MTypeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.MUnitsContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.TConceptCodeContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.TDescriptionContext;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.TTextContext;
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
 * 
 * @author DiSiD Team
 */
public class Fiebdc3ListenerImpl extends Fiebdc3BaseListener {

    private Database database = new Database();

    private Concept currentConcept;
    private Concept currentChildConcept;
    private Measurement currentMeasurement;
    private Line currentLine;

    // //////////////
    // V register //
    // //////////////
    @Override
    public void enterVCertDate(VCertDateContext ctx) {
        database.setCertDate(ctx.getText());
    }

    @Override
    public void enterVCertNum(VCertNumContext ctx) {
        database.setCertNum(Integer.valueOf(ctx.getText()));
    }

    @Override
    public void enterVCharset(VCharsetContext ctx) {
        database.setCharset(ctx.getText());
    }

    @Override
    public void enterVComments(VCommentsContext ctx) {
        database.setComments(ctx.getText());
    }

    @Override
    public void enterVFileProperty(VFilePropertyContext ctx) {
        database.setFileProperty(ctx.getText());
    }

    @Override
    public void enterVGeneratedBy(VGeneratedByContext ctx) {
        database.setGeneratedBy(ctx.getText());
    }

    @Override
    public void enterVHeader(VHeaderContext ctx) {
        database.setHeader(ctx.getText());
    }

    @Override
    public void enterVInfoType(VInfoTypeContext ctx) {
        database.setInfoType(Integer.valueOf(ctx.getText()));
    }

    @Override
    public void enterVVersionFormat(VVersionFormatContext ctx) {
        database.setFileFormat(ctx.getText());
    }

    // //////////////
    // C register //
    // //////////////
    @Override
    public void enterCCode(CCodeContext ctx) {
        String code = ctx.getText().trim();
        currentConcept = database.getConcept(code);
        if (currentConcept == null) {
            if (code.endsWith("##")) {
                currentConcept = database.addRootConcept(code);
            } else {
                currentConcept = database.addOrphanConcept(code);
            }
        }
    }

    @Override
    public void enterCDate(CDateContext ctx) {
        currentConcept.setLastUpdate(ctx.getText());
    }

    @Override
    public void enterCPrice(CPriceContext ctx) {
        currentConcept.setPrice(ctx.getText());
    }

    @Override
    public void enterCSummary(CSummaryContext ctx) {
        currentConcept.setSummary(ctx.getText());
    }

    @Override
    public void enterCType(CTypeContext ctx) {
        currentConcept.setType(ctx.getText());
    }

    @Override
    public void enterCUnit(CUnitContext ctx) {
        currentConcept.setMeasureUnit(ctx.getText());
    }

    @Override
    public void exitCConcept(CConceptContext ctx) {
        currentConcept = null;
    }

    // //////////////
    // D register //
    // //////////////
    @Override
    public void enterDParentCode(DParentCodeContext ctx) {
        String code = ctx.getText().trim();
        currentConcept = database.getAddConcept(code);
    }

    @Override
    public void enterDChildCode(DChildCodeContext ctx) {
        String childCode = ctx.getText();
        currentChildConcept = currentConcept.getConcept(childCode);
        if (currentChildConcept == null) {
            currentChildConcept = database.addChildConcept(childCode,
                    currentConcept);
        }
    }

    @Override
    public void enterDFactor(DFactorContext ctx) {
        currentChildConcept.setFactor(ctx.getText());
    }

    @Override
    public void enterDPerformance(DPerformanceContext ctx) {
        currentChildConcept.setPerformance(ctx.getText());
    }

    @Override
    public void exitDBreakdown(DBreakdownContext ctx) {
        currentConcept = null;
        currentChildConcept = null;
    }

    public Database getDatabase() {
        return database;
    }

    // //////////////
    // T register //
    // //////////////
    @Override
    public void enterTConceptCode(TConceptCodeContext ctx) {
        String code = ctx.getText().trim();
        currentConcept = database.getAddConcept(code);
    }

    @Override
    public void enterTDescription(TDescriptionContext ctx) {
        currentConcept.setDescription(ctx.getText());
    }

    @Override
    public void exitTText(TTextContext ctx) {
        currentConcept = null;
    }

    // //////////////
    // M register //
    // //////////////
    @Override
    public void enterMMeasurement(MMeasurementContext ctx) {
        this.currentMeasurement = database.addMeasurement();
    }

    @Override
    public void enterMParentCode(MParentCodeContext ctx) {
        currentMeasurement.setParentConcept(ctx.getText().trim());
    }

    @Override
    public void enterMChildCode(MChildCodeContext ctx) {
        database.setMeasurement(ctx.getText().trim(), currentMeasurement);
    }

    @Override
    public void enterMPosition(MPositionContext ctx) {
        currentMeasurement.addPosition(ctx.getText());
    }

    @Override
    public void enterMTotalMeasurement(MTotalMeasurementContext ctx) {
        currentMeasurement.setTotal(ctx.getText());
    }

    @Override
    public void enterMLine(MLineContext ctx) {
        currentLine = currentMeasurement.addLine();
    }

    @Override
    public void enterMType(MTypeContext ctx) {
        currentLine.setType(ctx.getText());
    }

    @Override
    public void enterMComment(MCommentContext ctx) {
        currentLine.setComment(ctx.getText());
    }

    @Override
    public void enterMUnits(MUnitsContext ctx) {
        currentLine.setUnits(ctx.getText());
    }

    @Override
    public void enterMLongitude(MLongitudeContext ctx) {
        currentLine.setLength(ctx.getText());
    }

    @Override
    public void enterMLatitude(MLatitudeContext ctx) {
        currentLine.setWidth(ctx.getText());
    }

    @Override
    public void enterMHeight(MHeightContext ctx) {
        currentLine.setHeight(ctx.getText());
    }

    @Override
    public void exitMLine(MLineContext ctx) {
        currentLine = null;
    }

    @Override
    public void enterMLabel(MLabelContext ctx) {
        currentMeasurement.setLabel(ctx.getText());
    }

    @Override
    public void exitMMeasurement(MMeasurementContext ctx) {
        currentMeasurement = null;
    }
}
