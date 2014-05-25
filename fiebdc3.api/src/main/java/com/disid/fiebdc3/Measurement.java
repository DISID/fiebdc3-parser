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

    List<MeasurementLine> measurements = new ArrayList<MeasurementLine>();

    String label;

    Measurement() {
        // Nothing to do
    }

    /**
     * Spec definition:<br/>
     * <i>POSICION: Posición del CONCEPTO_HIJO en la descomposición del
     * CONCEPTO_PADRE, este dato permite identificar la medición cuando la
     * descomposición del concepto padre incluye varios conceptos hijo con el
     * mismo CODIGO, la numeración de las posiciones comenzará con el 1.
     * 
     * El campo POSICION deberá especificarse siempre en intercambio de
     * presupuestos cuando éste sea completo y estructurado, e indicará el
     * camino completo de la medición descrita en la estructura del archivo. Por
     * ejemplo 3 \ 5 \ 2, indicará la medición correspondiente al capítulo 3 del
     * archivo; subcapítulo 5 del capítulo 3; y partida 2 del subcapítulo 5. En
     * mediciones no estructuradas este campo es opcional.</i>
     */
    public List<Integer> getPosition() {
        return position;
    }

    public void addPosition(Integer position) {
        this.position.add(position);
    }

    /**
     * Spec definition:<br/>
     * <i>MEDICION_TOTAL: Debe coincidir con el rendimiento del registro tipo
     * '~D' correspondiente. Incorpora el sumatorio del producto de unidades,
     * longitud, latitud y altura o el resultado de expresiones de cada línea,
     * al leer este registro se recalculará este valor.</i>
     */
    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     * Returns the measurements of the work chapter.
     * 
     * @return the measurements of the work chapter
     */
    public List<MeasurementLine> getMeasurements() {
        return measurements;
    }

    /**
     * Adds a new measurement.
     * 
     * @return the new added measurement
     */
    public MeasurementLine addMeasurement() {
        MeasurementLine measurement = new MeasurementLine();
        this.measurements.add(measurement);
        return measurement;
    }

    /**
     * Spec definition:<br/>
     * <i>ETIQUETA: Es opcional, surge de la necesidad de transmitir un
     * identificador de los conceptos (capítulos, subcapítulos o partidas). Este
     * identificador lo imprimen, diversos programas, en los listados de
     * mediciones o presupuesto de una Obra (por ejemplo, ‘2.10’, ‘A-27b’,
     * ‘001001’,…); siendo único para cada concepto (capítulo, subcapítulo o
     * partida) y, en general, diferente de la codificación de la base de datos
     * empleada para confeccionar el presupuesto (El ‘CODIGO_HIJO’ muchas veces
     * no aparece en los listados mencionados). </i>
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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

    public MeasurementLine addLine() {
        MeasurementLine line = new MeasurementLine();
        measurements.add(line);
        return line;
    }

    @Override
    public String toString() {
        return "Measurement {" + super.toString() + ", Position: " + position
                + ", Total: " + total + ", Label: " + label
                + ", Measurements: " + measurements + "}";
    }
}
