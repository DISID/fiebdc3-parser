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
grammar VInfo;

import Lexer;

/*
REGISTRO TIPO PROPIEDAD Y VERSION
~V   | [ PROPIEDAD_ARCHIVO ] | VERSION_FORMATO [ \ DDMMAAAA ] | [ PROGRAMA_EMISION ] | [ CABECERA ] \ { ROTULO_IDENTIFICACION \ } | [ JUEGO_CARACTERES ] | [ COMENTARIO         ] | [ TIPO INFORMACIÓN ] | [ NÚMERO CERTIFICACIÓN  ] | [  FECHA CERTIFICACIÓN ] |
ej:
~V   | CYPE INGENIEROS, S.A. | FIEBDC-3/2004                  | ARQUIMEDES           |                                            | ANSI                 | Certificación en curso | 3                    | 8                         |                          |
NOTA: Ignora posibles campos adicionales añadidos en versiones posteriores
*/
vInfo: 'V' FIELDSEP vFileProperty? FIELDSEP vVersionFormat (SUBFIELDSEP TEXT)? FIELDSEP vGeneratedBy? FIELDSEP vHeader? FIELDSEP vCharset? FIELDSEP vComments? FIELDSEP vInfoType? FIELDSEP vCertNum? FIELDSEP vCertDate? FIELDSEP (TEXT FIELDSEP)*;
      
/*
PROPIEDAD_ARCHIVO: Redactor de la base de datos u obra, fecha, …
*/
vFileProperty: TEXT;

/*
VERSION_FORMATO: VERSION del formato del archivo, la actual es FIEBDC-3/2004
*/
vVersionFormat: TEXT;

/*
PROGRAMA_EMISION: Programa y/o empresa que genera los ficheros en formato BC3.
*/
vGeneratedBy: TEXT;

/*
CABECERA: Título general de los ROTULOS_IDENTIFICACION.
*/
vHeader:  TEXT;

/*
JUEGO_CARACTERES: Asigna si el juego de caracteres a emplear es el definido 
para D.O.S., cuyos identificadores serán 850 ó 437, o es el definido para 
Windows, cuyo identificador será ANSI. En caso de que dicho campo esté vacío 
se interpretará, por omisión, que el juego de caracteres a utilizar será el 
850 por compatibilidad con versiones anteriores.
*/
vCharset: '850'
       | '437'
       | 'ANSI'
       | '';
/*
COMENTARIO: Contenido del archivo (base, obra...).
*/
vComments: TEXT;

/*
TIPO INFORMACIÓN: Índice del tipo de información a intercambiar. Se definen 
los siguientes tipos:
   1 Base de datos.
   2 Presupuesto.
   3 Certificación (a origen).
   4 Actualización de base de datos.
*/
vInfoType: TEXT;

/*
NÚMERO CERTIFICACIÓN: Valor numérico indicando el orden de la certificación 
(1, 2, 3,...) Solo tiene sentido cuando el tipo de información es Certificación
*/
vCertNum: TEXT;

/*
FECHA CERTIFICACIÓN:  Fecha de la certificación indicada en el campo número 
certificación. Solo tiene sentido cuando el tipo de información es Certificación.
La fecha se definirá con el mismo formato que el campo DDMMAAAA de este registro.
*/
vCertDate: TEXT;