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

import com.disid.fiebdc3.*;
import com.disid.fiebdc3.Database.Charset;
import com.disid.fiebdc3.antlr4.Fiebdc3Parser.*;

import java.util.Date;

/**
 * Listener for the Fiebdc3 parser events.
 * 
 * @author DiSiD Team
 */
public class Fiebdc3ListenerImpl extends Fiebdc3BaseListener {

    private Database database = new Database();

    private Concept currentConcept;
    private ConceptBreakdown currentBreakdown;
    private ConceptBreakdown currentChildBreakdown;
    private Measurement currentMeasurement;
    private MeasurementLine currentLine;

    public Database getDatabase() {
        return database;
    }

    // //////////////
    // V register //
    // //////////////
    @Override
    public void enterVCertDate(VCertDateContext ctx) {
        String certDate = ctx.getText().trim();
        Date value = ParseUtils.parseDate(certDate);
        database.setCertDate(value);
    }

    @Override
    public void enterVCertNum(VCertNumContext ctx) {
        database.setCertNum(Integer.valueOf(ctx.getText().trim()));
    }

    @Override
    public void enterVCharset(VCharsetContext ctx) {
        Charset value = Charset.parseCharset(ctx.getText().trim());
        database.setCharset(value);
    }

    @Override
    public void enterVComments(VCommentsContext ctx) {
        database.setComments(ctx.getText());
    }

    @Override
    public void enterVFileProperty(VFilePropertyContext ctx) {
        database.setFileProperty(ctx.getText().trim());
    }

    @Override
    public void enterVGeneratedBy(VGeneratedByContext ctx) {
        database.setGeneratedBy(ctx.getText().trim());
    }

    @Override
    public void enterVHeader(VHeaderContext ctx) {
        database.setHeader(ctx.getText().trim());
    }

    @Override
    public void enterVInfoType(VInfoTypeContext ctx) {
        String infoType = ctx.getText().trim();
        Integer value = Integer.valueOf(infoType);
        database.setInfoType(value);
    }

    @Override
    public void enterVVersionFormat(VVersionFormatContext ctx) {
        database.setFileFormat(ctx.getText().trim());
    }

    // //////////////
    // C register //
    // //////////////
    @Override
    public void enterCCode(CCodeContext ctx) {
        String code = ctx.getText().trim().replace("#", "");
        currentConcept = database.getOrAddConcept(code);
    }

    @Override
    public void enterCDate(CDateContext ctx) {
        String certDate = ctx.getText().trim();
        Date value = ParseUtils.parseDate(certDate);
        currentConcept.addLastUpdate(value);
    }

    @Override
    public void enterCPrice(CPriceContext ctx) {
        String price = ctx.getText().trim();
        Float value = Float.parseFloat(price);
        currentConcept.addPrice(value);
    }

    @Override
    public void enterCSummary(CSummaryContext ctx) {
        currentConcept.setSummary(ctx.getText().trim());
    }

    @Override
    public void enterCType(CTypeContext ctx) {
        currentConcept.setType(ctx.getText().trim());
    }

    @Override
    public void enterCUnit(CUnitContext ctx) {
        currentConcept.setMeasureUnit(ctx.getText().trim());
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

        if (code.endsWith("##")) {
            currentBreakdown = database.setRoot(code.replace("#", ""));
        } else {
            currentBreakdown = database.addConceptBreakdown(code.replace("#", ""));
        }
    }

    @Override
    public void enterDChildCode(DChildCodeContext ctx) {
        String childCode = ctx.getText().trim().replace("#", "");

        currentChildBreakdown = database.addConceptBreakdown(
                currentBreakdown.getConceptCode().replace("#", ""), childCode);
    }

    @Override
    public void enterDFactor(DFactorContext ctx) {
        String text = ctx.getText().trim();
        if (text.length() > 0) {
            Float value = Float.parseFloat(text);
            currentChildBreakdown.setFactor(value);
        }
    }

    @Override
    public void enterDPerformance(DPerformanceContext ctx) {
        String text = ctx.getText().trim();
        Float value = Float.parseFloat(text);
        currentChildBreakdown.setPerformance(value);
    }

    @Override
    public void exitDBreakdown(DBreakdownContext ctx) {
        currentBreakdown = null;
        currentChildBreakdown = null;
    }

    // //////////////
    // T register //
    // //////////////
    @Override
    public void enterTConceptCode(TConceptCodeContext ctx) {
        String code = ctx.getText().trim().replace("#", "");
        currentConcept = database.getOrAddConcept(code);
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
        this.currentMeasurement = database.createMeasurement();
    }

    @Override
    public void enterMParentCode(MParentCodeContext ctx) {
        currentMeasurement.setParentConcept(ctx.getText().trim().replace("#", ""));
    }

    @Override
    public void enterMChildCode(MChildCodeContext ctx) {
        String conceptCode = ctx.getText().trim().replace("#", "");
        database.setMeasurement(conceptCode, currentMeasurement);
    }

    @Override
    public void enterMPosition(MPositionContext ctx) {
        String text = ctx.getText().trim();
        Integer value = Integer.parseInt(text);
        currentMeasurement.addPosition(value);
    }

    @Override
    public void enterMTotalMeasurement(MTotalMeasurementContext ctx) {
        String text = ctx.getText().trim();
        Float value = Float.parseFloat(text);
        currentMeasurement.setTotal(value);
    }

    @Override
    public void enterMLine(MLineContext ctx) {
        currentLine = currentMeasurement.addLine();
    }

    @Override
    public void enterMType(MTypeContext ctx) {
        String text = ctx.getText().trim();
        Integer value = Integer.parseInt(text);
        currentLine.setType(value);
    }

    @Override
    public void enterMComment(MCommentContext ctx) {
        currentLine.setComment(ctx.getText());
    }

    @Override
    public void enterMUnits(MUnitsContext ctx) {
        String text = ctx.getText();
        Float value = Float.parseFloat(text);
        currentLine.setUnits(value);
    }

    @Override
    public void enterMLongitude(MLongitudeContext ctx) {
        String text = ctx.getText().trim();
        Float value = Float.parseFloat(text);
        currentLine.setLength(value);
    }

    @Override
    public void enterMLatitude(MLatitudeContext ctx) {
        String text = ctx.getText().trim();
        Float value = Float.parseFloat(text);
        currentLine.setWidth(value);
    }

    @Override
    public void enterMHeight(MHeightContext ctx) {
        String text = ctx.getText().trim();
        Float value = Float.parseFloat(text);
        currentLine.setHeight(value);
    }

    @Override
    public void exitMLine(MLineContext ctx) {
        currentLine = null;
    }

    @Override
    public void enterMLabel(MLabelContext ctx) {
        currentMeasurement.setLabel(ctx.getText().trim());
    }

    @Override
    public void exitMMeasurement(MMeasurementContext ctx) {
        currentMeasurement = null;
    }
}
