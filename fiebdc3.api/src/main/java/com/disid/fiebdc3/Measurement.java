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
 * Data of a measurement of a Fiebdc3 database.
 * 
 * @author DiSiD Team
 */
public class Measurement {

    String parentConcept;

    String concept;

    List<Integer> position = new ArrayList<Integer>();

    Float total;

    List<Line> lines = new ArrayList<Line>();

    String label;

    public String getParentConcept() {
        return parentConcept;
    }

    public void setParentConcept(String parentConcept) {
        this.parentConcept = parentConcept;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public void addPosition(Integer position) {
        this.position.add(position);
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<Line> getLines() {
        return lines;
    }

    public Line addLine() {
        Line line = new Line();
        this.lines.add(line);
        return line;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Measurement {" + "Parent concept code: " + parentConcept
                + ", Concept code: " + concept + ", Position: " + position
                + ", Total: " + total + ", Label: " + label + ", Lines: "
                + lines + "}";
    }

    public static class Line {

        Integer type;

        String comment;

        Float units;

        Float length;

        Float width;

        Float height;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Float getUnits() {
            return units;
        }

        public void setUnits(Float units) {
            this.units = units;
        }

        public Float getLength() {
            return length;
        }

        public void setLength(Float length) {
            this.length = length;
        }

        public Float getWidth() {
            return width;
        }

        public void setWidth(Float width) {
            this.width = width;
        }

        public Float getHeight() {
            return height;
        }

        public void setHeight(Float height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "Line {" + "Type: " + type + ", Comment: " + comment
                    + ", Units: " + units + ", Length: " + length + ", Width: "
                    + width + ", Height: " + height + "}";
        }

    }
}
