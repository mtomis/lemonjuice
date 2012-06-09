/*
 *  lemonjuice - Java Template Engine.
 *  Copyright (C) 2009-2011 Manuel Tomis manuel@codegremlins.com
 *
 *  This library is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.codegremlins.lemonjuice.util;

public class Functions {
    public static boolean compareEqual(Object left, Object right) {
        if (left == null) {
        	return right == null || Boolean.FALSE.equals(right);
        } else if (right == null) {
        	return left == null || Boolean.FALSE.equals(left);
        }
        
        if (left instanceof Number && right instanceof Number) {
            if (left instanceof Long) {
                return (((Number)left).longValue() == ((Number)right).longValue());
            } else if (left instanceof Integer) {
                return (((Number)left).intValue() == ((Number)right).intValue());
            } else {
                return left.equals(right);
            }
        }
        
        return left.equals(right);
    }
}
