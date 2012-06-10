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
import java.util.Iterator;

import com.codegremlins.lemonjuice.Template;
import com.codegremlins.lemonjuice.TemplateContext;

@SuppressWarnings("unchecked")
class ForInlineElement extends Element {
    private final String[] names;
    private final Element[] elements;
    private final Element[] functions;
    private final Element otherwise;

    public ForInlineElement(String[] names, Element[] elements, Element[] functions, Element otherwise) {
        this.names = names;
        this.elements = elements;
        this.functions = functions;
        this.otherwise = otherwise;
    }

    @Override
    public void print(Writer out, TemplateContext model) throws Exception {
        Object[] values = new Object[elements.length];
        Object[] parameters = new Object[elements.length];
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                values[i] = elements[i].evaluate(model);
            }
            
            if (values[i] instanceof Template) {
                values[i] = ((Template)values[i]).evaluate(model); 
            }            
            
            if (values[i] instanceof Iterable) {
                values[i] = ((Iterable)values[i]).iterator();
            }
        }
        
        boolean first = true;
        
        int j = 0;
        int index = 0;
        for (;;) {
            int count = 0;
            
            for (int i = 0; i < values.length; i++) {
                Object object = values[i];
                if (object instanceof Iterator) {
                    Iterator iterator = (Iterator)object;
                    if (iterator.hasNext()) {
                        object = iterator.next();
                    } else {
                        object = "";
                        count++;
                    }
                } else {
                    count++;
                }
                
                parameters[i] = object;
            }

            if (count == values.length) {
                if (first && otherwise != null) {
                    otherwise.print(out, model);
                }
                break;
            }
            
            Element template = functions[j];
            if (template != null) {
                TemplateContext context = pushContext(template, model, parameters);
                context.set("_0", index);
                context.set("_1" , (index + 1));
                context.set("_first", first);
                
                template.print(out, context);
            }

            first = false;
            j = (j + 1) % functions.length;
            index++;
        }
    }
    
    private TemplateContext pushContext(Element element, TemplateContext model, Object[] parameters) throws Exception {
        TemplateContext context = model.child();
        String[] keys = names;

        for (int i = 0; i < keys.length; i++) {
            if (i >= parameters.length) {
                break;
            }
            context.set(keys[i], parameters[i]);
        }
        
        return context;
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        return null;
    }
}