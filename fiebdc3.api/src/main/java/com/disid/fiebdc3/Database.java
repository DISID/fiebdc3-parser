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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contains the data of a Fiebdc 3 file.
 * <p>
 * A database has is composed of a root {@link Concept}, which itself might be
 * composed of child {@link Concept}s:
 * </p>
 * 
 * <pre>
 * database
 * '-> list of concepts
 * '-> root concept breakdown (related to a concept)
 *     |-> child concept breakdown 1 (related to a concept, might have chapter or work package as children)
 *     |   '-> grandson concept breakdown (related to a concept)
 *     |       |-> measurement 1
 *     |       |-> measurement 2
 *     |       ...
 *     |       '-> measurement n 
 *     |-> child concept breakdown 2 (related to a concept)
 *     ...
 *     '-> child concept breakdown n (related to a concept)
 * </pre>
 * 
 * <p>
 * This class is also a factory for the elements it includes, so {@link Concept}
 * s and {@link Measurement}s can only be created through a {@link Database}.
 * </p>
 * 
 * @author DiSiD Team
 */
public class Database {

    /**
     * Enumeration of the available charset options for the Fiebdc3 file.
     * 
     * @author DiSiD Team
     */
    public enum Charset {
        ANSI("ANSI"), C850("850"), C437("437");

        private final String code;

        /**
         * Create a new Charset with the given code
         * 
         * @param code
         *            of the charset
         */
        private Charset(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }

        /**
         * Returns the Charset with the given code or null if not available.
         * 
         * @param code
         *            of the charset
         * @return the Charset with the code
         */
        public static Charset parseCharset(String code) {
            for (Charset c : Charset.values()) {
                if (c.code.equals(code)) {
                    return c;
                }
            }
            return null;
        }
    }

    private String fileProperty;

    private String fileFormat;

    private String generatedBy;

    private String header;

    private Charset charset = Charset.C850;

    private String comments;

    private int infoType;

    private int certNum;

    private Date certDate;

    private ConceptBreakdown root;

    private Map<String, ConceptBreakdown> orphanBreakdowns = new HashMap<String, ConceptBreakdown>();

    private Set<Measurement> orphanMeasurements = new HashSet<Measurement>();

    private Map<String, Concept> concepts = new HashMap<String, Concept>();

    /**
     * Spec definition:<br/>
     * <i> PROPIEDAD_ARCHIVO: Redactor de la base de datos u obra, fecha, … </i>
     * 
     * @return the file writer or owner
     */
    public String getFileProperty() {
        return fileProperty;
    }

    public void setFileProperty(String fileProperty) {
        this.fileProperty = fileProperty;
    }

    /**
     * Spec definition:<br/>
     * <i> VERSION_FORMATO: VERSION del formato del archivo, la actual es
     * FIEBDC-3/2007 </i>
     */
    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * Spec definition:<br/>
     * <i> PROGRAMA_EMISION: Programa y/o empresa que genera los ficheros en
     * formato BC3. </i>
     */
    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    /**
     * Spec definition:<br/>
     * <i> CABECERA: Título general de los ROTULOS_IDENTIFICACION. </i>
     */
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Spec definition:<br/>
     * <i> JUEGO_CARACTERES: Asigna si el juego de caracteres a emplear es el
     * definido para D.O.S., cuyos identificadores serán 850 ó 437, o es el
     * definido para Windows, cuyo identificador será ANSI. En caso de que dicho
     * campo esté vacío se interpretará, por omisión, que el juego de caracteres
     * a utilizar será el 850 por compatibilidad con versiones anteriores. </i>
     */
    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Spec definition:<br/>
     * <i> COMENTARIO: Contenido del archivo (base, obra...). </i>
     */
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Spec definition:<br/>
     * <i> TIPO INFORMACIÓN: Índice del tipo de información a intercambiar. Se
     * definen los siguientes tipos: 1 Base de datos. 2 Presupuesto. 3
     * Certificación (a origen). 4 Actualización de base de datos. </i>
     */
    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    /**
     * Spec definition:<br/>
     * <i> NÚMERO CERTIFICACIÓN: Valor numérico indicando el orden de la
     * certificación (1, 2, 3,...) Solo tiene sentido cuando el tipo de
     * información es Certificación. </i>
     */
    public int getCertNum() {
        return certNum;
    }

    public void setCertNum(int certNum) {
        this.certNum = certNum;
    }

    /**
     * Spec definition:<br/>
     * <i> FECHA CERTIFICACIÓN: Fecha de la certificación indicada en el campo
     * número certificación. Solo tiene sentido cuando el tipo de información es
     * Certificación. La fecha se definirá con el mismo formato que el campo
     * DDMMAAAA de este registro </i>
     */
    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    /**
     * Returns the database root {@link Concept}.
     * 
     * @return the main root concept
     */
    public ConceptBreakdown getRoot() {
        return root;
    }

    private void setRoot(ConceptBreakdown root) {
        this.root = root;
    }

    /**
     * Creates and sets a new root concept breakdown for the database.
     * 
     * @param code
     *            to identify the concept of the breakdown
     * @return the new root concept breakdown
     */
    public ConceptBreakdown setRoot(String code) {
        ConceptBreakdown bd = orphanBreakdowns.remove(code);
        if (bd == null) {
            bd = new ConceptBreakdown(code);
        }
        setRoot(bd);
        return bd;
    }

    public ConceptBreakdown addConceptBreakdown(String code) {
        ConceptBreakdown bd = getExistingConceptBreakdown(code);
        if (bd == null) {
            bd = new ConceptBreakdown(code);
            orphanBreakdowns.put(code, bd);
        }
        return bd;
    }

    public ConceptBreakdown addConceptBreakdown(String parentCode, String code) {
        if (parentCode == null) {
            return addConceptBreakdown(code);
        }
        ConceptBreakdown parent = addConceptBreakdown(parentCode);
        
        // It's already an organized ConceptBreakdown
        ConceptBreakdown bd = parent.getCodeBreakdown(code);
        
        // It's still an orphan breakdown
        if (bd == null) {
            bd = orphanBreakdowns.remove(code);
            
            // It still does not exist
            if (bd == null) {
                bd = new ConceptBreakdown(parentCode, code);
            }
            parent.addChildBreakdownInfo(bd);
            bd.setParentConceptCode(parentCode);
        }
        
        return bd;
    }

    private ConceptBreakdown getExistingConceptBreakdown(String code) {
        ConceptBreakdown bd = null;
        if (getRoot() != null) {
            bd = getRoot().getCodeBreakdown(code);
        }

        if (bd == null) {
            bd = orphanBreakdowns.get(code);
        }

        return bd;
    }

    /**
     * Returns the concept with the given code in the database.
     * 
     * @param code
     *            of the {@link Concept}
     * @return the concept with the given code
     */
    public Concept getConcept(String code) {
        return concepts.get(code);
    }

    /**
     * Returns the concept related to a concept breakdown.
     * 
     * @param bd
     *            the concept breakdown
     * @return the related concept
     */
    public Concept getConcept(ConceptBreakdown bd) {
        return getConcept(bd.getConceptCode());
    }

    /**
     * Returns the concept with the given code in the database, or creates a new
     * one if it didn't exist.
     * 
     * @param code
     *            of the {@link Concept}
     * @return the concept with the given code
     */
    public Concept getOrAddConcept(String code) {
        Concept concept = getConcept(code);
        return concept == null ? addConcept(code) : concept;
    }

    /**
     * Creates and adds a concept with the given code to the database.
     * 
     * @param code
     *            of the {@link Concept}
     * @return the new concept with the given code
     */
    private Concept addConcept(String code) {
        Concept concept = new Concept(code);
        concepts.put(code, concept);
        return concept;
    }

    /**
     * Returns if there are any orphan concept breakdowns pending to be
     * organized.
     * 
     * @return if there are any orphan concept breakdowns pending to be
     *         organized
     */
    public boolean hasOrphanedConceptBreakdowns() {
        return orphanBreakdowns != null && !orphanBreakdowns.isEmpty();
    }

    /**
     * Returns if there are any orphan measurements pending to be setted to a
     * concept.
     * 
     * @return if there are any orphan measurements pending to be setted to a
     *         concept
     */
    public boolean hasOrphanedMeasurements() {
        return orphanMeasurements != null && !orphanMeasurements.isEmpty();
    }

    /**
     * Creates a new measurement and adds it to the database, without bein
     * setted to a concept.
     * 
     * @return the new Measurement
     */
    public Measurement createMeasurement() {
        Measurement measurement = new Measurement();
        orphanMeasurements.add(measurement);
        return measurement;
    }

    /**
     * Sets a Measurement to the related Concept.
     * 
     * @param conceptCode
     *            code of the Concept to set the Measure to
     * @param measurement
     *            to set to the Concept
     */
    public void setMeasurement(String conceptCode, Measurement measurement) {
        ConceptBreakdown bd = addConceptBreakdown(
                measurement.getParentConcept(), conceptCode);
        bd.setMeasurement(measurement);
        orphanMeasurements.remove(measurement);
    }

    @Override
    public String toString() {
        return "Fiebdc3Database {" + "File property of: " + fileProperty
                + ", Format version: " + fileFormat + ", Generated by: "
                + generatedBy + ", Header: " + header + ", Charset: " + charset
                + ", Comments: " + comments + ", Information type: " + infoType
                + ", Certification number: " + certNum
                + ", Certification date: " + certDate + ", Concepts: "
                + concepts + ", Root Concept Breakdown: \n\t" + root
                + "\n, Orphan Breakdowns: " + orphanBreakdowns
                + ", Orphan Measurements: " + orphanMeasurements + "}";
    }
}
