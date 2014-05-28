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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Breakdown information a Fiebdc 3 database concept. A concept of chapter type
 * might be composed of other subchapters or concepts of type work chapter. This
 * class models the information of each subconcept in the breakdown of the
 * parent concept.
 * 
 * @author DiSiD Team
 */
public class ConceptBreakdown {

    private String parentConceptCode;

    private String conceptCode;

    private Float factor = 1.0f;

    private Float performance = 1.0f;

    private Set<ConceptBreakdown> childBreakdown = new LinkedHashSet<ConceptBreakdown>();

    private Measurement measurement;

    /**
     * Creates a root or orphan breakdown information entry for a concept.
     * 
     * @param conceptCode
     *            which uniquely identifies the subchapter's code
     */
    ConceptBreakdown(String conceptCode) {
        this.parentConceptCode = null;
        this.conceptCode = cleanCode(conceptCode);
    }

    /**
     * Creates a breakdown information entry for a concept.
     * 
     * @param parentConceptCode
     *            which uniquely identifies a parent chapter's code
     * @param conceptCode
     *            which uniquely identifies the subchapter's code
     */
    ConceptBreakdown(String parentConceptCode, String conceptCode) {
        this.parentConceptCode = cleanCode(parentConceptCode);
        this.conceptCode = cleanCode(conceptCode);
    }

    /**
     * Spec definition:<br/>
     * <i>CODIGO_HIJO: CODIGO de cada concepto que interviene en la
     * descomposición</i>
     */
    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String code) {
        this.conceptCode = cleanCode(code);
    }

    /**
     * Spec definition:<br/>
     * <i>CODIGO_PADRE: CODIGO del concepto descompuesto</i>
     */
    public String getParentConceptCode() {
        return parentConceptCode;
    }

    public void setParentConceptCode(String parentConceptCode) {
        this.parentConceptCode = parentConceptCode;
    }

    /**
     * Spec definition:<br/>
     * <i>FACTOR: Factor de rendimiento, por defecto 1.0</i>
     */
    public Float getFactor() {
        return factor;
    }

    public void setFactor(Float factor) {
        this.factor = factor;
    }

    /**
     * Spec definition:<br/>
     * <i>RENDIMIENTO: Número de unidades, rendimiento o medición, por defecto
     * 1.0</i>
     */
    public Float getPerformance() {
        return performance;
    }

    public void setPerformance(Float performance) {
        this.performance = performance;
    }

    protected boolean isMyConceptCode(String code) {
        String cleanedCode = cleanCode(code);

        return (this.conceptCode != null && this.conceptCode
                .equals(cleanedCode));
    }

    /**
     * Just return the code, don't do anything more. Older versions remove the
     * '#' symbol that may contain the code, but currently this code is
     * commented.
     * 
     * @param code the code.
     * @return the code cleaned.
     */
    private String cleanCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        while (code.endsWith("#")) {
//            code = code.substring(0, code.length() - 1);
//        }
        return code;
    }

    /**
     * Breakdown of this chapter in subchapters or work chapters.
     * 
     * @return the list of child BreakdownInfos.
     */
    public Iterable<ConceptBreakdown> getChildBreakdownInfos() {
        return childBreakdown;
    }

    /**
     * Adds a new child BreakdownInfo concept
     * 
     * @param breakdownInfo
     *            the child breakdownInfo
     * @return if it was already included as a breakdownInfo
     */
    boolean addChildBreakdownInfo(ConceptBreakdown breakdownInfo) {
        if (childBreakdown == null) {
            childBreakdown = new LinkedHashSet<ConceptBreakdown>();
        }
        return childBreakdown.add(breakdownInfo);
    }

    /**
     * Returns this or a child BreakdownInfo with the given code, which might
     * include '\' separators to define a path of concept codes.
     * 
     * @param conceptCode
     *            the code to find
     * @return the found code's BreakdownInfo or null
     */
    public ConceptBreakdown getCodeBreakdown(String conceptCode) {
        ConceptBreakdown bdinfo = isMyConceptCode(conceptCode) ? this : null;

        if (bdinfo != null) {
            return bdinfo;
        }

        // If its not this chapter's concept code, look in the child
        // breakdowns
        int pathPosition = conceptCode.indexOf('\\');
        if (pathPosition > 0) {
            String parentCode = conceptCode.substring(0, pathPosition);
            String childCode = conceptCode.substring(pathPosition + 1);
            if (isMyConceptCode(parentCode)) {
                return getChildBreakdown(childCode);
            }
        } else {
            return getChildBreakdown(conceptCode);
        }

        return null;
    }

    private ConceptBreakdown getChildBreakdown(String childCode) {
        if (childBreakdown != null) {
            for (ConceptBreakdown childBreakdownInfo : getChildBreakdownInfos()) {
                ConceptBreakdown foundBreakdownInfo = childBreakdownInfo
                        .getCodeBreakdown(childCode);
                if (foundBreakdownInfo != null) {
                    return foundBreakdownInfo;
                }
            }
        }
        return null;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public boolean isWorkPackage() {
        return measurement != null;
    }

    @Override
    public String toString() {
        return "ConceptBreakdown {" + "Parent concept code: "
                + parentConceptCode
                + ", Concept code: " + conceptCode + ", Factor: " + factor
                + ", Performance: " + performance + ", Measurement: "
                + measurement + ", Child BreakdownInfos: \n\t" + childBreakdown
                + "}";
    }
}
