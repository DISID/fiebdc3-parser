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
lexer grammar Lexer;

/*
NOTE: standard definition:
"El fin de línea será el ESTANDAR de los archivos MS-DOS (ASCII-13 y ASCII-10).
El fin de archivo se marcará según el mismo ESTANDAR (ASCII-26). El único 
carácter de control adicional que se permitirá será el tabulador (ASCII-9)."
*/
//NEWLINE:'\r'? '\n';

/*
NOTE: standard definition:
"Se ignorarán los caracteres blancos (32), tabuladores (9) y de fin de línea 
(13 y 10), delante de los separadores < ~ >, < | > y < \ >."
*/
WS : [\r\n]+ -> skip;

REGSEP     : (' ' | '\t' | '\n' | '\r')* '~' ;  // register separator
FIELDSEP   : (' ' | '\t' | '\n' | '\r')* '|' ;  // field separator
SUBFIELDSEP: (' ' | '\t' | '\n' | '\r')* '\\' ; // subfield separator

/*
NOTE: standard definition:
"Las fechas se definirán en el formato DDMMAAAA: DD representa el día con dos 
dígitos, MM el mes y AAAA el año. Si la fecha tiene 6 ó menos dígitos, el año 
se representará con dos dígitos (AA), interpretándose con el criterio “80/20”. 
Esto es, cualquier año que sea igual o superior a 80 corresponderá al siglo XX 
y cualquier año que sea menor de 80 corresponderá al siglo XXI.  Si la fecha 
tiene menos de 5 dígitos representa mes y año únicamente (MMAA), si tiene menos
de tres, solo el año (AA). Si se identifica la fecha con un número impar de
dígitos, se completará con el carácter cero por la izquierda. Para representar
una fecha sin un día o mes específico, se utilizará un doble cero en cada caso.

Ejemplos:
            12062000          12 de junio de 2000
            120699             12 de junio de 1999
            00061281          junio de 1281
            061281             6 de diciembre de 1981
            401                  abril de 2001
"
NOTE: unable to distinguish from a INT or TEXT definition, so no lexer rule 
added for dates.
*/

/*
NOTE: unable to distinguish TEXT fields with INT files, so lexer rule disabled.
*/
// INT: [0-9]+ ('.' [0-9]+)?;

/*
NOTE: standard definition:
"El juego de caracteres a emplear en los campos CODIGO será el definido por 
MS-DOS 6.0, incluyendo A-Z, a-z, 0-9, ñ, Ñ,< . > (ASCII-46), < $ > (ASCII-36), 
< # > (ASCII-35), < %> (ASCII-37), < & > (ASCII-38), < _ > (ASCII-95). 
Excluyendo cualquier otro carácter como espacio, tabulador, etc."

NOTE: use TEXT since we can't distinguish from a TEXT definition.
*/
// CODE: [a-zA-Z0-9ñÑ.$#%&_]+;

/*
Main lexer rule, all type conversions will be performed in the Listener.
*/
TEXT: (~[~|\\])+;
