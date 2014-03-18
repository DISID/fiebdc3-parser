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
grammar TText;

import Lexer;

/*
REGISTRO TIPO TEXTO
~T | CODIGO_CONCEPTO | TEXTO_DESCRIPTIVO |
*/
tText: 'T' FIELDSEP tConceptCode FIELDSEP tDescription FIELDSEP;

/*
CODIGO_CONCEPTO: CODIGO del concepto descrito
*/
tConceptCode: TEXT;
 
/*
TEXTO_DESCRIPTIVO: Texto descriptivo del concepto sin limitación de tamaño. El
texto podrá contener caracteres fin de línea (ASCII-13 + ASCII-10) que se
mantendrán al reformatearlo.
*/
tDescription: TEXT;
