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

package com.codegremlins.lemonjuice.engine;

import java.util.List;

import com.codegremlins.lemonjuice.TemplateContext;

@SuppressWarnings("unchecked")
abstract public class FunctionElement extends Element {
    private Element[] parameters;

    public FunctionElement(Element[] parameters) {
        this.parameters = parameters;
    }

    protected Element[] parameters() {
        return parameters;
    }

    protected Object[] values(TemplateContext model) throws Exception {
        Object[] values = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            values[i] = parameters[i].evaluate(model);
        }
        
        return values;
    }
    
    protected String string(TemplateContext model, int position) throws Exception {
        if (parameters.length <= position) {
            return null;
        }
        
        Object value = parameters[position].evaluate(model);
        if (value instanceof String) {
            return (String)value;
        } else if (value instanceof Number) {
            return value.toString();
        }
        
        return null;
    }

    protected long number(TemplateContext model, int position) throws Exception {
        if (parameters.length <= position) {
            return 0;
        }
        
        Object value = parameters[position].evaluate(model);
        if (value instanceof String) {
            try {
                return Long.parseLong((String)value);
            } catch (Exception ex) {
                return 0;
            }
        } else if (value instanceof Number) {
            return ((Number)value).longValue();
        } else if (value instanceof Boolean) {
            return Boolean.TRUE.equals(value) ? 1 : 0;
        }
        
        return 0;
    }

    protected List list(TemplateContext model, int position) throws Exception {
        if (parameters.length <= position) {
            return null;
        }
        
        Object value = parameters[position].evaluate(model);
        if (value instanceof List) {
            return (List)value;
        }
        
        return null;
    }
}