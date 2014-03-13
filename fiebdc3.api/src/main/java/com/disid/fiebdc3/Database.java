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

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the data of a Fiebdc 3 file.
 * @author DiSiD Team
 */
/**
 * @author DiSiD Team
 * 
 */
public class Database {

    /**
     * PROPIEDAD_ARCHIVO: Redactor de la base de datos u obra, fecha, …
     */
    private String fileProperty;

    /**
     * VERSION_FORMATO: VERSION del formato del archivo, la actual es
     * FIEBDC-3/2007
     */
    private String fileFormat;

    /**
     * PROGRAMA_EMISION: Programa y/o empresa que genera los ficheros en formato
     * BC3.
     */
    private String generatedBy;

    /**
     * CABECERA: Título general de los ROTULOS_IDENTIFICACION.
     */
    private String header;

    /**
     * JUEGO_CARACTERES: Asigna si el juego de caracteres a emplear es el
     * definido para D.O.S., cuyos identificadores serán 850 ó 437, o es el
     * definido para Windows, cuyo identificador será ANSI. En caso de que dicho
     * campo esté vacío se interpretará, por omisión, que el juego de caracteres
     * a utilizar será el 850 por compatibilidad con versiones anteriores.
     */
    private String charset = "ANSI";

    /**
     * COMENTARIO: Contenido del archivo (base, obra...).
     */
    private String comments;

    /**
     * TIPO INFORMACIÓN: Índice del tipo de información a intercambiar. Se
     * definen los siguientes tipos: 1 Base de datos. 2 Presupuesto. 3
     * Certificación (a origen). 4 Actualización de base de datos.
     */
    private int infoType;

    /**
     * NÚMERO CERTIFICACIÓN: Valor numérico indicando el orden de la
     * certificación (1, 2, 3,...) Solo tiene sentido cuando el tipo de
     * información es Certificación.
     */
    private int certNum;

    /**
     * FECHA CERTIFICACIÓN: Fecha de la certificación indicada en el campo
     * número certificación. Solo tiene sentido cuando el tipo de información es
     * Certificación. La fecha se definirá con el mismo formato que el campo
     * DDMMAAAA de este registro
     */
    private String certDate;

    private Concept rootConcept;

    private Map<String, Concept> orphanConcepts = new HashMap<String, Concept>();

    public String getFileProperty() {
        return fileProperty;
    }

    public void setFileProperty(String fileProperty) {
        this.fileProperty = fileProperty;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public int getCertNum() {
        return certNum;
    }

    public void setCertNum(int certNum) {
        this.certNum = certNum;
    }

    public String getCertDate() {
        return certDate;
    }

    public void setCertDate(String certDate) {
        this.certDate = certDate;
    }

    public Concept getRootConcept() {
        return rootConcept;
    }

    void setRootConcept(Concept rootConcept) {
        this.rootConcept = rootConcept;
    }

    /**
     * Returns the concept with the given code in the database.
     * 
     * @param code
     *            of the {@link Concept}
     * @return the concept with the given code
     */
    public Concept getConcept(String code) {
        Concept concept = rootConcept == null ? null : rootConcept
                .getConcept(code);

        if (concept == null) {
            concept = orphanConcepts.get(code);
        }

        return concept;
    }

    /**
     * Add a new root concept for the database.
     * 
     * @param code
     *            to identify the concept
     * @return the new root concept
     */
    public Concept addRootConcept(String code) {
        Concept concept = orphanConcepts.remove(code);
        if (concept == null) {
            concept = new Concept(code);
        }
        setRootConcept(concept);
        return concept;
    }

    /**
     * Creates a new concept, which is included in a set of concepts still not
     * organized: it is not a root concept, nor it has been added as a child
     * concept.
     * 
     * @param code
     *            of the concept
     * @return the created concept
     */
    public Concept addOrphanConcept(String code) {
        Concept concept = orphanConcepts.get(code);
        if (concept == null) {
            concept = new Concept(code);
            orphanConcepts.put(code, concept);
        }
        return concept;
    }

    /**
     * Creates a new concept, which is added as a child of the given concept.
     * 
     * @param code
     *            of the concept
     * @return the created concept
     */
    public Concept addChildConcept(String code, Concept parentConcept) {
        Concept concept = orphanConcepts.remove(code);
        if (concept == null) {
            concept = new Concept(code);
        }
        parentConcept.addChildConcept(concept);
        return concept;
    }

    @Override
    public String toString() {
        return "Fiebdc3Database {" + "File property of: " + fileProperty
                + ", Format version: " + fileFormat + ", Generated by: "
                + generatedBy + ", Header: " + header + ", Charset: " + charset
                + ", Comments: " + comments + ", Information type: " + infoType
                + ", Certification number: " + certNum
                + ", Certification date: " + certDate + ", Root concept: \n\t"
                + rootConcept + "}";
    }
}
