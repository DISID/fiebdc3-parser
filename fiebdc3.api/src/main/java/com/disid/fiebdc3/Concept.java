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
import java.util.Iterator;
import java.util.List;

/**
 * Concept of the Fiebdc 3 database.
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

    private List<Concept> childConcepts;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Iterator<Concept> getChildConcepts() {
        return childConcepts == null ? null : childConcepts.iterator();
    }

    public boolean addChildConcept(Concept concept) {
        if (childConcepts == null) {
            childConcepts = new ArrayList<Concept>();
        }
        return childConcepts.add(concept);
    }

    public Concept getConcept(String code) {
        if (this.code != null && this.code.equals(code)) {
            return this;
        } else if (childConcepts != null) {
            for (Concept childConcept : childConcepts) {
                Concept concept = childConcept.getConcept(code);
                if (concept != null) {
                    return concept;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Concept {" + "Code: " + code + ", Summary: " + summary
                + ", Type: " + type + ", Measure unit: " + measureUnit
                + ", Price: " + price + ", Last update: " + lastUpdate
                + ", Child concepts: " + childConcepts + "}";
    }
}
