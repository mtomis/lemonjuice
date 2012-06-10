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

// TODO: rename
class IndirectPropertyElement extends Element {
    private Element element;
    private Element key;

    public IndirectPropertyElement(Element element, Element key) {
        this.element = element;
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object evaluate(TemplateContext model) throws Exception {
        Object keyResult = key.evaluate(model);
        Object value = element.evaluate(model);
        
        if (value == null || keyResult == null) {
            return null; // or throw exception?
        }
        
        String name = "";
        if (keyResult instanceof String) {
            name = (String)keyResult;
        } else if (keyResult instanceof Number) {
            if (value instanceof List) {
                return ((List)value).get(((Number)keyResult).intValue());
            }
            name = "" + keyResult;
        } else {
            return null; //FIXME
        }
        
        return getValue(value, name);
    }

    @Override
    public void visit(TemplateElementVisitor context) throws Exception {
        context.visit(this);
    }
}