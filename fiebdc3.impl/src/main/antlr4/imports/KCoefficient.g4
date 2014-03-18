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
grammar KCoefficient;

import Lexer;

/*
REGISTRO TIPO COEFICIENTES.
~K | { DN \ DD \ DS \ DR \ DI \ DP \ DC \ DM \ DIVISA \ } | CI \ GG \ BI \ BAJA \ IVA | { DRC \ DC \ DRO \ DFS \ DRS \ DFO \ DUO \ DI \ DES \ DN \ DD \ DS \ DIVISA \ } | [ n ] |
ej:
~K |      \ 2  \ 3  \ 3  \ 2  \ 2  \ 2  \ 2  \ EUR    \   | 0  \ 13 \ 6  \      \ 21  |       \ 2  \     \     \ 3   \     \ 2   \ 2  \ 2   \    \ 2  \ 3  \ EUR    \   |       |
*/
kCoefficient: 'K' FIELDSEP (TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP)* FIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? FIELDSEP (TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP)* FIELDSEP TEXT? FIELDSEP;


/*
DN: Decimales del campo número de partes iguales de la hoja de mediciones. 
Por defecto 2 decimales.
*/

/*
DD: Decimales de dimensiones de las tres magnitudes de la hoja de mediciones. 
Por defecto 2 decimales.
*/

/*
DS: Decimales de la línea de subtotal o total de mediciones. 
Por defecto 2 decimales.
*/

/*
DR: Decimales de rendimiento y factor en una descomposición. 
Por defecto 3 decimales.
*/

/*
DI: Decimales del importe resultante de multiplicar rendimiento x precio del concepto. 
Por defecto 2 decimales
*/

/*
DP: Decimales del importe resultante del sumatorio de los costes directos del concepto. 
Por defecto 2 decimales
*/

/*
DC: Decimales del importe total del concepto. (CD+CI). Por defecto 2 decimales
*/

/*
DM: Decimales del importe resultante de multiplicar la medición total del concepto por su precio. 
Por defecto 2 decimales
*/

/*
DIVISA: Es la divisa expresada en el mismo modo que las abreviaturas utilizadas 
por el BCE (Banco Central Europeo), que en su caso deberán coincidir con las del
registro ~V. En el Anexo 6 se indican las actuales.
*/

/*
CI: Costes Indirectos, expresados en porcentaje.
*/

/*
GG: Gastos Generales de la Empresa, expresados en porcentaje.
*/

/*
BI: Beneficio Industrial, expresado en porcentaje.
*/

/*
BAJA: Coeficiente de baja o alza de un presupuesto de adjudicación, expresado 
en porcentaje.
*/

/*
IVA: Impuesto del Valor Añadido, expresado en porcentaje.
*/

/*
DRC: Decimales del rendimiento y del factor de rendimiento de un presupuesto, 
y decimales del resultado de su multiplicación. Por defecto 3 decimales.
*/

/*
DC: Decimales del importe de un presupuesto, de sus capitulos, subcapitulos, etc. 
y líneas de medición (unidades de obra excluidas), y decimales de los importes 
resultantes de multiplicar el rendimiento (o medición) total del presupuesto, 
sus capitulos, subcapitulos, etc. y líneas de medición (unidades de obra 
excluidas) por sus precios respectivos. Por defecto 2 decimales.
*/

/*
DFS: Decimales de los factores de rendimiento de las unidades de obra y de los
elementos compuestos. Por defecto 3 decimales.
*/

/*
DRS: Decimales de los rendimientos de las unidades de obra y de los elementos
compuestos, y decimales del resultado de la multiplicación de dichos
rendimientos por sus respectivos factores. Por defecto 3 decimales.
*/

/*
DUO: Decimales del coste total de las unidades de obra. Por defecto 2 decimales.
*/

/*
DI: Decimales de los importes resultantes de multiplicar los rendimientos 
totales de los elementos compuestos y/o elementos simples por sus respectivos 
precios, decimales del importe resultante del sumatorio de los costes directos 
de la unidad de obra y decimales de los costes indirectos. Decimales de los 
sumatorios sobre los que se aplican los porcentajes. Por defecto 2 decimales.
*/

/*
DES: Decimales del importe de los elementos simples. Por defecto 2 decimales.
*/

/*
DN: Decimales del campo número de partes iguales de la hoja de mediciones.
Por defecto 2 decimales.
*/

/*
DD: Decimales de dimensiones de las tres magnitudes de la hoja de mediciones. 
Por defecto 2 decimales.
*/

/*
DS: Decimales del total de mediciones. Por defecto 2 decimales.
*/

/*
DSP: Decimales de la línea de subtotal de mediciones. Por defecto 2 decimales.
*/

/*
DEC: Decimales del importe de los elementos compuestos. Por defecto 2.
*/

/*
DIVISA: Es la divisa expresada en el mismo modo que las abreviaturas utilizadas por el BCE (Banco Central Europeo), que en su caso deberán coincidir con las del registro ~V. En el Anexo 6 se indican las actuales.
*/

/*
n: Es el número de la opción de la función BdcGloParNumero que se refiere al concepto divisa.
*/
