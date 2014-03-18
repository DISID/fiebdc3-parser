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
grammar Fiebdc3;

import Lexer, VInfo, KCoefficient, CConcept, TText, DBreakdown, MMeasurement;

/* 
Cada archivo estará compuesto de registros, zonas de texto entre el carácter 
de principio de registro < ~ > (ASCII-126) y el siguiente principio de registro
o fin de archivo. Los archivos deberán contener registros completos, es decir, 
la división de archivos se deberá realizar al comienzo de un registro (carácter < ~ >).

Se ignorará cualquier INFORMACION entre el último separador de campos de un 
registro (carácter < | >) o el comienzo del archivo y el comienzo del siguiente 
registro (carácter < ~>).
*/
database: (REGSEP record)+;

/*
Cada registro estará compuesto de campos separados por caracteres < | > 
(ASCII-124). Todo campo con INFORMACION tendrá que finalizar con el separador de
campos y el registro deberá contener todos los separadores de campos anteriores,
aunque no contengan INFORMACION. No es necesario disponer de finalizadores de 
los campos posteriores al último con INFORMACION.

El primer campo de cada registro es la cabecera de registro, una letra mayúscula
que identifica el tipo de registro.

Cada campo estará compuesto de subcampos separados por caracteres < \ > 
(ASCII-92). El separador final, entre el último dato de un campo y el fin de
campo es opcional.
Los campos vacíos se considerarán SIN INFORMACION, no con INFORMACION nula, 
esto permite producir archivos de actualización que contengan únicamente la 
INFORMACION en alguno de sus campos y, por supuesto, el CODIGO de referencia.
Para anular un campo numérico deberá aparecer explícitamente el valor 0 (cero).
Para anular un campo alfanumérico deberá aparecer explícitamente el ROTULO NUL.
*/
record: vInfo
      | kCoefficient
      | cConcept
      | tText
      | dBreakdown
      | mMeasurement
      ;