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

import java.io.Writer;

import com.codegremlins.lemonjuice.Template;
import com.codegremlins.lemonjuice.TemplateContext;

class IncludeElement extends Element {
    private Element[] values;
    private Template parent;

    public IncludeElement(Template parent, Element[] values) {
        this.parent = parent;
        this.values = values;
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        for (Element element : values) {
            Template template = load(element, model);

            if (template != null) {
                template.evaluate(model);
            }
        }
        
        return null;
    }

    @Override
    public void print(Writer out, TemplateContext model) throws Exception {
        for (Element element : values) {
            Template template = load(element, model);

            if (template != null) {
                template.print(out, model);
            }
        }
    }
    
    private Template load(Element element, TemplateContext model) throws Exception {
        Object value = element.evaluate(model);
        if (value instanceof Template) {
            value = ((Template)value).evaluate(model);
        } 
        
        if (value != null) {
            value = value.toString();
        }
        
        String name = parent.getLocation() + value;
        return model.find(name);
    }

    @Override
    public void visit(TemplateElementVisitor context) throws Exception {
        context.visit(this);
    }
}