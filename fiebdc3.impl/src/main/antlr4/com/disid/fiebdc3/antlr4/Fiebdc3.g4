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

vInfoType: TEXT;
vCertNum: TEXT;
vCertDate: TEXT;

/*
REGISTRO TIPO COEFICIENTES.
~K | { DN \ DD \ DS \ DR \ DI \ DP \ DC \ DM \ DIVISA \ } | CI \ GG \ BI \ BAJA \ IVA | { DRC \ DC \ DRO \ DFS \ DRS \ DFO \ DUO \ DI \ DES \ DN \ DD \ DS \ DIVISA \ } | [ n ] |
ej:
~K |      \ 2  \ 3  \ 3  \ 2  \ 2  \ 2  \ 2  \ EUR    \   | 0  \ 13 \ 6  \      \ 21  |       \ 2  \     \     \ 3   \     \ 2   \ 2  \ 2   \    \ 2  \ 3  \ EUR    \   |       |
TODO: arreglar si es necesario
*/
kCoefficient: 'K' FIELDSEP (TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP)* FIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? FIELDSEP (TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP TEXT? SUBFIELDSEP)* FIELDSEP TEXT? FIELDSEP;

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
            
/*
REGISTRO TIPO MEDICIONES
~M | [ CODIGO_PADRE \ ] CODIGO_HIJO | { POSICION \ } | MEDICION_TOTAL | { TIPO \ COMENTARIO \ UNIDADES \ LONGITUD \ LATITUD \ ALTURA \ } | [ETIQUETA] |
ej:
~M | 502mar#        \   PP14052     | 1\1\1\1\2\     | 0              |                                                                  |
~M | 502mar#        \   PP07015     | 1\1\1\1\3\     | 3.57           |        \ 0 a 7      \ 3        \ 0.21     \         \        \   |
*/
mMeasurement: 'M' FIELDSEP ( mParentCode SUBFIELDSEP )+ mChildCode FIELDSEP ( mPosition SUBFIELDSEP )* FIELDSEP mTotalMeasurement FIELDSEP (mLine)* FIELDSEP (mLabel FIELDSEP)?;

mLine: mType? SUBFIELDSEP mComment? SUBFIELDSEP mUnits? SUBFIELDSEP mLongitude? SUBFIELDSEP mLatitude? SUBFIELDSEP mHeight? SUBFIELDSEP;

mParentCode: TEXT;

mChildCode: TEXT;

mPosition: TEXT;

mTotalMeasurement: TEXT;

mLabel: TEXT;

mType: TEXT;
mComment: TEXT;
mUnits: TEXT;
mLongitude: TEXT;
mLatitude: TEXT;
mHeight: TEXT;

/**** LEXER ****/

/*
El fin de línea será el ESTANDAR de los archivos MS-DOS (ASCII-13 y ASCII-10).
El fin de archivo se marcará según el mismo ESTANDAR (ASCII-26). El único 
carácter de control adicional que se permitirá será el tabulador (ASCII-9).
*/
//NEWLINE:'\r'? '\n';

/*
Se ignorarán los caracteres blancos (32), tabuladores (9) y de fin de línea 
(13 y 10), delante de los separadores < ~ >, < | > y < \ >.
*/
WS : [\r\n]+ -> skip;

REGSEP     : (' ' | '\t' | '\n' | '\r')* '~' ;  // register separator
FIELDSEP   : (' ' | '\t' | '\n' | '\r')* '|' ;  // field separator
SUBFIELDSEP: (' ' | '\t' | '\n' | '\r')* '\\' ; // subfield separator


// NOTA: no cambiar el orden de TEXT, FLOAT y TEXT, ya que uno contiene 
// al otro y se emplean por orden de aparición.

/*
Las fechas se definirán en el formato DDMMAAAA: DD representa el día con dos 
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

Nota: usamos la misma definición que TEXT, y ya se procesa desde código, pues será
muy difícil distinguir en algunos casos.
*/

// TEXT: [0-9]+ ('.' [0-9]+)?;

/*
El juego de caracteres a emplear en los campos CODIGO será el definido por 
MS-DOS 6.0, incluyendo A-Z, a-z, 0-9, ñ, Ñ,< . > (ASCII-46), < $ > (ASCII-36), 
< # > (ASCII-35), < %> (ASCII-37), < & > (ASCII-38), < _ > (ASCII-95). 
Excluyendo cualquier otro carácter como espacio, tabulador, etc.

Nota: usamos siempre TEXT porque realmente nos da igual desde la aplicación.
CODE: [a-zA-Z0-9ñÑ.$#%&_]+;
*/

TEXT: (~[~|\\])+;
