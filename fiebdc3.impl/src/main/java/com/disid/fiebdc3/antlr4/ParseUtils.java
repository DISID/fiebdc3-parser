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

package com.disid.fiebdc3.antlr4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities to parse values from string in the FIEBDC3 format.
 * 
 * @author DiSiD Team
 */
public class ParseUtils {

    private static DateFormat fullDateFormat = new SimpleDateFormat("ddMMyyyy");
    private static DateFormat mediumDateFormat = new SimpleDateFormat("ddMMyy");
    private static DateFormat monthYearDateFormat = new SimpleDateFormat("MMyy");
    private static DateFormat singleMonthYearDateFormat = new SimpleDateFormat(
            "Myy");
    private static DateFormat yearDateFormat = new SimpleDateFormat("yy");

    /**
     * Parse dates with the format of FIEBDC3 dates:
     * <p>
     * <cite> DD representa el día con dos dígitos, MM el mes y AAAA el año. Si
     * la fecha tiene 6 ó menos dígitos, el año se representará con dos dígitos
     * (AA), interpretándose con el criterio “80/20”. Esto es, cualquier año que
     * sea igual o superior a 80 corresponderá al siglo XX y cualquier año que
     * sea menor de 80 corresponderá al siglo XXI. Si la fecha tiene menos de 5
     * dígitos representa mes y año únicamente (MMAA), si tiene menos de tres,
     * solo el año (AA). Si se identifica la fecha con un número impar de
     * dígitos, se completará con el carácter cero por la izquierda. Para
     * representar una fecha sin un día o mes específico, se utilizará un doble
     * cero en cada caso. </cite>
     * </p>
     */
    public static Date parseDate(String certDate) {
        String dateToParse = certDate;
        DateFormat dateFormat = fullDateFormat;
        switch (certDate.length()) {
        case 1:
        case 2:
            dateFormat = yearDateFormat;
            break;
        case 3:
            dateFormat = singleMonthYearDateFormat;
            break;
        case 4:
            dateToParse = replaceDoubleZero(dateToParse, 0);
            dateFormat = monthYearDateFormat;
            break;
        case 5:
        case 6:
            dateToParse = replaceDoubleZero(dateToParse, 0);
            dateToParse = replaceDoubleZero(dateToParse, 2);
            dateFormat = mediumDateFormat;
            break;
        case 7:
        case 8:
        default:
            dateToParse = replaceDoubleZero(dateToParse, 0);
            dateToParse = replaceDoubleZero(dateToParse, 2);
            dateFormat = fullDateFormat;
        }

        try {
            Date date = dateFormat.parse(dateToParse);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replaces a "00" in a date String with a "01". This is because the FIEBDC3
     * format allows to set the day or month value as "00" when it is ignored,
     * but we are parsing a full date and so we need a valid one, and parsers
     * don't admit "00" as day or month.
     * 
     * @param value
     *            the string to perform the replacement on
     * @param start
     *            where to look for the "00" in te text
     * @return a text with "00" value replaced if found
     */
    public static String replaceDoubleZero(String value, int start) {
        if (value.regionMatches(start, "00", 0, 2)) {
            String first = start > 0 ? value.substring(0, start) : "";
            String last = value.substring(start + 2);
            return first + "01" + last;
        }
        return value;
    }
}
