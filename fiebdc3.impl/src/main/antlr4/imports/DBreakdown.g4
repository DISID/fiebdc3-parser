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
grammar DBreakdown;

import Lexer;

/*
REGISTRO TIPO DESCOMPOSICION
~D | CODIGO_PADRE | < CODIGO_HIJO \ [ FACTOR ] \ [ RENDIMIENTO ] \ > |
Ej:
~D | AZ346OBRA##  | 01#           \            \ 1                \02#\\1\03#\\1\04#\\1\|
*/
dBreakdown: 'D' FIELDSEP dParentCode FIELDSEP (dChildCode SUBFIELDSEP dFactor SUBFIELDSEP dPerformance SUBFIELDSEP)+ FIELDSEP;

/*
CODIGO_PADRE: CODIGO del concepto descompuesto.
*/
dParentCode: TEXT;
 
/*
CODIGO_HIJO: CODIGO de cada concepto que interviene en la descomposición.
*/
dChildCode: TEXT;
 
/*
FACTOR: Factor de rendimiento, por defecto 1.0
*/
dFactor: TEXT?;

/*
RENDIMIENTO: Número de unidades, rendimiento o medición, por defecto 1.0
*/
dPerformance: TEXT?;
