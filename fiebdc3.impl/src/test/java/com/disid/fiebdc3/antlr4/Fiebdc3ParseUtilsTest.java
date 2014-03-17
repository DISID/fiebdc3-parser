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

import static com.disid.fiebdc3.antlr4.ParseUtils.replaceDoubleZero;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Unit tests for the {@link ParseUtils} class.
 * 
 * @author DiSiD Team
 */
public class Fiebdc3ParseUtilsTest {

    /**
     * Test method for
     * {@link com.disid.fiebdc3.antlr4.ParseUtils#parseDate(java.lang.String)}
     * . Check sample values in the FIEBDC3 spec work as expected:
     * <p>
     * <cite>
     * <dl>
     * <dt>12062000</dt>
     * <dd>12 de junio de 2000</dd>
     * <dt>120699</dt>
     * <dd>12 de junio de 1999</dd>
     * <dt>00061281</dt>
     * <dd>junio de 1281</dd>
     * <dt>061281</dt>
     * <dd>6 de diciembre de 1981</dd>
     * <dt>401</dt>
     * <dd>abril de 2001</dd>
     * </dl>
     * </cite>
     * </p>
     */
    @Test
    public void testParseDate() {
        String[] dateTexts = {
                // 12 de junio de 2000
                "12062000",
                // 12 de junio de 1999
                "120699",
                // junio de 1281
                "00061281",
                // 6 de diciembre de 1981
                "061281",
                // abril de 2001
                "0401",
                // abril de 2001
                "401" };
        Date[] dates = {
                // 12 de junio de 2000
                new GregorianCalendar(2000, 6 - 1, 12).getTime(),
                // 12 de junio de 1999
                new GregorianCalendar(1999, 6 - 1, 12).getTime(),
                // junio de 1281
                new GregorianCalendar(1281, 6 - 1, 1).getTime(),
                // 6 de diciembre de 1981
                new GregorianCalendar(1981, 12 - 1, 6).getTime(),
                // abril de 2001
                new GregorianCalendar(2001, 4 - 1, 1).getTime(),
                // abril de 2001
                new GregorianCalendar(2001, 4 - 1, 1).getTime() };

        for (int i = 0; i < dates.length; i++) {
            assertEquals(dates[i], ParseUtils.parseDate(dateTexts[i]));
        }

    }

    /**
     * Test method for
     * {@link com.disid.fiebdc3.antlr4.ParseUtils#replaceDoubleZero(String, int)}
     */
    @Test
    public void restReplaceDoubleZero() throws Exception {
        assertEquals("01012014", replaceDoubleZero("00012014", 0));

        assertEquals("01012014", replaceDoubleZero("01002014", 2));

        assertEquals("01012014",
                replaceDoubleZero(replaceDoubleZero("00002014", 0), 2));

        assertEquals("01012014",
                replaceDoubleZero(replaceDoubleZero("00002014", 2), 0));

        assertEquals("010114", replaceDoubleZero("000114", 0));

        assertEquals("010114", replaceDoubleZero("010014", 2));

        assertEquals("010114",
                replaceDoubleZero(replaceDoubleZero("000014", 0), 2));

        assertEquals("010114",
                replaceDoubleZero(replaceDoubleZero("000014", 2), 0));

        assertEquals("0114", replaceDoubleZero("0014", 0));
    }
}
