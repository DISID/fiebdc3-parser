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
import java.util.Date;
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

    private List<Float> prices = new ArrayList<Float>();

    private List<Date> lastUpdates = new ArrayList<Date>();

    private String type;

    private Float factor = 1.0f;

    private Float performance = 1.0f;

    private String description;

    private List<Concept> childConcepts;
    
    private Measurement measurement;

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

    public List<Float> getPrices() {
        return prices;
    }

    public void addPrice(Float price) {
        this.prices.add(price);
    }

    /**
     * Return the main (first) price of the concept.
     * 
     * @return the main (first) price of the concept
     */
    public Float getMainPrice() {
        return prices.get(0);
    }

    public List<Date> getLastUpdates() {
        return lastUpdates;
    }

    public void addLastUpdate(Date lastUpdate) {
        this.lastUpdates.add(lastUpdate);
    }

    /**
     * Return the main (first) lastUpdate of the concept.
     * 
     * @return the main (first) lastUpdate of the concept
     */
    public Date getMainLastUpdate() {
        return lastUpdates.get(0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getFactor() {
        return factor;
    }

    public void setFactor(Float factor) {
        this.factor = factor;
    }

    public Float getPerformance() {
        return performance;
    }

    public void setPerformance(Float performance) {
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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
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
                + ", Prices: " + prices + ", Last updates: " + lastUpdates
                + ", Factor: " + factor + ", Performance: " + performance
                + ", Description: " + description
                + ", Measurement: " + measurement
                + ", Child concepts: \n\t" + childConcepts + "}";
    }
}
