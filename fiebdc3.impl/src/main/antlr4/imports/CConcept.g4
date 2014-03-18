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
grammar CConcept;

import Lexer;

/*
REGISTRO TIPO CONCEPTO
~C | CODIGO { \ CODIGO } | [ UNIDAD ] | [ RESUMEN ] | { PRECIO \ } | { FECHA \ } | [ TIPO ] |
Ej:
~C | AZ346OBRA##         |            | Conserv...  | 1409291.73   | 260713      | 0        |
*/
cConcept: 'C' FIELDSEP cCode (SUBFIELDSEP TEXT)* FIELDSEP cUnit? FIELDSEP cSummary? FIELDSEP (cPrice? | (cPrice SUBFIELDSEP)+) FIELDSEP (cDate? | (cDate SUBFIELDSEP?)+) FIELDSEP cType FIELDSEP;
     
/*
CODIGO: CODIGO del concepto descrito. Un concepto puede tener varios CODIGOs 
que actuarán como sinónimos, este mecanismo permite integrar distintos sistemas
de clasificación. Puede tener un máximo de 20 caracteres.

Para distinguir el concepto tipo raíz de un archivo, así como los conceptos tipo
capítulo, se ampliará su CODIGO con los caracteres '##' y '#' respectivamente;
quedando dicha NOTACION reflejada obligatoriamente en el registro tipo ~C ,
siendo opcional en los restantes registros del mismo concepto.

Las referencias a un CODIGO con y sin # y/o ##, se entienden únicas a un mismo
concepto.

Únicamente puede haber un concepto raíz en una base de  datos u obra.

Si un código cuenta con un carácter '#' intercalado, se entenderá que 
corresponde al conjunto CODIGO_ENTIDAD # CODIGO_CONCEPTO definido en el 
registro de Tipo Relación Comercial (registro ~O) o en la función BdcComercCodigo.
*/
cCode: TEXT;

/*
UNIDAD: Unidad de medida. Existe una relación de unidades de medida recomendadas,
elaborada por la Asociación de Redactores de Bases de Datos de CONSTRUCCION. 
Véase el Anexo 7 sobre Unidades de Medida.
*/
/*
cUnit: 'm'   //metro
     | 'm2'  //metro cuadrado
     | 'm3'  //metro cúbico
     | 'kg'  //kilogramo
     | 'km'  //kilómetro
     | 't'   //tonelada
     | 'l'   //litro
     | 'h'   //hora
     | 'd'   //día
     | 'a'   //área
     | 'Ha'  //hectárea
     | 'cm3' //centímetro cúbico
     | 'cm2' //centímetro cuadrado
     | 'dm3' //decímetro cúbico
     | 'u'   //unidad
     | 'mu'  //mil unidades
     | 'cu'  //cien unidades
     | 'mes' //mes
    ;
*/
cUnit: TEXT;

/*
RESUMEN: Resumen del texto descriptivo. Cada soporte indicará el número de
caracteres que admite en su campo resumen. Se recomienda un máximo de 64 caracteres.
*/
cSummary: TEXT;

/*
PRECIO: Precio del concepto. Un concepto puede tener varios precios alternativos
que representen distintas épocas, ámbitos geográficos, etc., definidos
biunívocamente respecto al campo [CABECERA \ {ROTULO_IDENTIFICACION\} del
registro ~V. Cuando haya más de un precio se asignarán secuencialmente a cada
ROTULO definido; si hay más ROTULOS que precios, se asignará a aquellos el 
último precio definido. En el caso que el concepto posea descomposición, este
precio será el resultado de dicha descomposición y se proporcionará, de forma
obligatoria, para permitir su comprobación. En caso de discrepancia, tendrá 
preponderancia el resultado obtenido por la descomposición, tal como se indica
en el registro Tipo Descomposición, ~D, y complementariamente se podría informar
al usuario de dicha situación. Esto se aplica  también a los conceptos tipo
capítulo y concepto raíz de una Obra o Presupuesto. Como excepción a esta regla
está el intercambio de mediciones no estructuradas (véase la descripción del
registro Tipo Mediciones, ~M).
*/
cPrice: TEXT;
       
/*
FECHA: Fecha de la última actualización del precio. Cuando haya más de una fecha
se asignarán secuencialmente a cada precio definido, si hay más precios que
fechas, los precios sin su correspondiente fecha tomarán la última fecha definida.
*/
cDate: TEXT;

/*
TIPO: Tipo de concepto, Inicialmente se reservan los siguientes tipos:
0 (Sin clasificar) 1 (Mano de obra), 2 (Maquinaria y medios aux.), 3 (Materiales).
También se permite (y aconseja) utilizar la clasificación indicada por el BOE y
la CNC en índices y fórmulas polinómicas de revisión de precios así como los
aconsejados por la Asociación de Redactores de Bases de Datos de la Construcción.
En el Anexo 4 aparecen los tipos actualmente vigentes.
*/
cType: TEXT;
