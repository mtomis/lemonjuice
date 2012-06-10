/*
 *  lemonjuice - Java Template Engine.
 *  Copyright (C) 2009-2012 Manuel Tomis manuel@codegremlins.com
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

package com.codegremlins.lemonjuice.engine.builtins;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.FunctionElement;
import com.codegremlins.lemonjuice.util.Functions;

public class EqualsFunction extends FunctionElement {
    public EqualsFunction(Element[] parameters) {
        super(parameters);
    }
    
    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        Object last = null;
        boolean first = true;

        for (Element element : parameters()) {
            Object value = element.evaluate(model);
            if (value == null) {
                value = Boolean.FALSE;
            }
            
            if (!first) {
            	if (!Functions.compareEqual(last, value)) {
            		return false;
            	}
            } else {
                last = value;
                first = false;
            }
        }
        
        return true;
    }    
}