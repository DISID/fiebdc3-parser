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

import java.util.ArrayList;
import java.util.List;

/**
 * Concept of a Fiebdc 3 database.
 * 
 * @author DiSiD Team
 */
public class Concept {

    private String code;

    private String measureUnit;

    private String summary;

    private String price;

    private String lastUpdate;

    private String type;

    private String factor;

    private String performance;

    private String description;

    private List<Concept> childConcepts;

    /**
     * Creates a concept with the given code.
     * 
     * @param code
     *            which uniquely identifies a concept
     */
    Concept(String code) {
        this.code = cleanCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = cleanCode(code);
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Iterable<Concept> getChildConcepts() {
        return childConcepts;
    }

    boolean addChildConcept(Concept concept) {
        if (childConcepts == null) {
            childConcepts = new ArrayList<Concept>();
        }
        return childConcepts.add(concept);
    }

    public Concept getConcept(String code) {
        String parentCode = code;
        String childCode = code;
        int pathPosition = code.indexOf('\\');
        if (pathPosition > 0) {
            parentCode = code.substring(0, pathPosition);
            childCode = code.substring(pathPosition + 1);
            if (isMyCode(parentCode)) {
                return getChildConcept(childCode);
            }
            return null;
        }

        if (isMyCode(parentCode)) {
            return this;
        } else {
            return getChildConcept(childCode);
        }
    }

    private Concept getChildConcept(String childCode) {
        if (childConcepts != null) {
            for (Concept childConcept : childConcepts) {
                Concept concept = childConcept.getConcept(childCode);
                if (concept != null) {
                    return concept;
                }
            }
        }
        return null;
    }

    private boolean isMyCode(String code) {
        String cleanedCode = cleanCode(code);

        return (this.code != null && this.code.equals(cleanedCode));
    }

    private String cleanCode(String code) {
        while (code.endsWith("#")) {
            code = code.substring(0, code.length() - 1);
        }
        return code;
    }

    @Override
    public String toString() {
        return "Concept {" + "Code: " + code + ", Summary: " + summary
                + ", Type: " + type + ", Measure unit: " + measureUnit
                + ", Price: " + price + ", Last update: " + lastUpdate
                + ", Factor: " + factor + ", Performance: " + performance
                + ", Description: " + description
                + ", Child concepts: \n\t" + childConcepts + "}";
    }
}
