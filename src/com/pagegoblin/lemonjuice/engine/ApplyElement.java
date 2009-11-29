/*
 *  lemonjuice - Java Template Engine.
 *  Copyright (C) 2009 Manuel Tomis support@pagegoblin.com
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

package com.pagegoblin.lemonjuice.engine;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import com.pagegoblin.lemonjuice.Printer;
import com.pagegoblin.lemonjuice.Template;
import com.pagegoblin.lemonjuice.TemplateContext;
import com.pagegoblin.lemonjuice.TemplateFunction;

class ApplyElement extends Element {
    private Element element;
    private Element[] parameters;
    private String[] keys;
    private Set<String> keySet;

    public ApplyElement(Element element, Element[] parameters) {
        this.element = element;
        this.parameters = parameters;
    }

    public ApplyElement(Element element, Element[] parameters, String[] keys) {
        this.element = element;
        this.parameters = parameters;
        this.keys = keys;
        this.keySet = new HashSet<String>();
        
        if (keys != null) {
            for (String key : keys) {
                keySet.add(key);
            }
        }
    }

    @Override
    public void print(Writer out, TemplateContext model) throws Exception {
        Object value = element.evaluate(model);
        
        if (value instanceof String) {
            out.write((String)value);
        } else if (value instanceof Printer) {
            ((Printer)value).print(out);
        } else if (value instanceof Template) {
            Template template = (Template)value;
            template.print(out, pushContext(template, model));
        } else if (value instanceof TemplateFunction) {
            TemplateFunction template = (TemplateFunction)value;
            
            Object[] values = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                values[i] = parameters[i].evaluate(model);
                if (values[i] instanceof Template) {
                    values[i] = ((Template)values[i]).evaluate(model);                    
                }
            }
              
            Object result = template.evaluate(values);
            if (result != null) {
                out.write(result.toString());
            }
        } else {
            error("Cannot apply value as macro: " + value);
        }
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        Object value = element.evaluate(model);
        
        if (value instanceof String) {
            return value;
        } else if (value instanceof Printer) {
            StringWriter out = new StringWriter();
            ((Printer)value).print(out);
            return out.toString();
        } else if (value instanceof Template) {
            Template template = (Template)value;
            StringWriter out = new StringWriter();
            template.print(out, pushContext(template, model));
            
            return out.toString();
        } else if (value instanceof TemplateFunction) {
            TemplateFunction template = (TemplateFunction)value;
            
            Object[] values = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                values[i] = parameters[i].evaluate(model);
            }
              
            return template.evaluate(values);
        } else {
            return error("Cannot apply value as macro: " + value);
        }
    }

    protected TemplateContext pushContext(Template template, TemplateContext model) throws Exception {
        if (keys != null) {
            TemplateContext context = model.child();
            for (int i = 0; i < keys.length; i++) {
                context.set(keys[i], parameters[i].evaluate(model));
            }

            String[] parameters = template.getParameters();
            Element[] defaults = template.getDefaults();

            if (parameters != null && defaults != null) {
                for (int i = 0; i < parameters.length; i++) {
                    String key = parameters[i];
                    if (!keySet.contains(key) && defaults[i] != null) {
                        context.set(key, defaults[i].evaluate(model));
                    }
                }
            }
            return context;
        } else if (template.getParameters() != null) {
            TemplateContext context = model.child();
            String[] keys = template.getParameters();

            int i = 0;
            for (; i < keys.length; i++) {
                if (i >= parameters.length) {
                    break;
                }
                context.set(keys[i], parameters[i].evaluate(model));
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
            return model.child();
        }
    }
}