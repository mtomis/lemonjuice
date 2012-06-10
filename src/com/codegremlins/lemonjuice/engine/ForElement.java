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
class ForElement extends Element {
    private final Element[] elements;
    private final Element[] functions;
    private final Element otherwise;
    private boolean functionMode = true;

    public ForElement(Element[] elements, Element[] names, boolean functionMode, Element otherwise) {
        this.elements = elements;
        this.functions = names;
        this.functionMode = functionMode;
        this.otherwise = otherwise;
    }

    @Override
    public void print(Writer out, TemplateContext model) throws Exception {
        Object[] values = new Object[elements.length];
        Object[] parameters = new Object[elements.length];
        for (int i = 0; i < elements.length; i++) {
            values[i] = elements[i].evaluate(model);
            
            if (values[i] instanceof Template) {
                values[i] = ((Template)values[i]).evaluate(model); 
            }            
            
            if (values[i] instanceof Iterable) {
                values[i] = ((Iterable)values[i]).iterator();
            }
        }
        
        boolean first = true;
        if (functionMode) {
            Template[] templates = new Template[functions.length];
            for (int i = 0; i < templates.length; i++) {
                Object v = functions[i].evaluate(model);
                if (v instanceof Template) {
                    templates[i] = (Template)v; 
                }
            }

            int index = 0;
            int j = 0;
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
                
                Template template = templates[j];
                if (template != null) {
                    TemplateContext context = pushContext(template, model, parameters);
                    context.set("_0", index);
                    context.set("_1" , (index + 1));
                    context.set("_first", first);
//                    if (index == values.length - 1) {
//                        context.set("_last", true);
//                    }
                    template.print(out, context);
                }
                
                first = false;
                j = (j + 1) % templates.length;
                index++;
            }
        } else {
            
        }
    }
    
    private TemplateContext pushContext(Template template, TemplateContext model, Object[] parameters) throws Exception {
        if (template.getParameters() != null) {
            TemplateContext context = model.child();
            String[] keys = template.getParameters();

            int i = 0;
            for (; i < keys.length; i++) {
                if (i >= parameters.length) {
                    break;
                }
                context.set(keys[i], parameters[i]);
            }
            
            if (template.getDefaults() != null) {
                Element[] defaults = template.getDefaults();
                for (; i < defaults.length; i++) {
                    if (defaults[i] != null) {
                        context.set(keys[i], defaults[i].evaluate(model));
                    }
                }
            }
            
            return context;
            
        } else {
            return model;
        }
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        return null;
    }

    @Override
    public void visit(TemplateElementVisitor context) throws Exception {
        context.visit(this);
    }
}