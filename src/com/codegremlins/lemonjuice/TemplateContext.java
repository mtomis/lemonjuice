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

package com.codegremlins.lemonjuice;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TemplateContext {
    private TemplateLookup lookup = DefaultTemplateLookup.INSTANCE;
    private TemplateContext parent;
    private Map<String, Object> model = new HashMap<String, Object>();
    private TemplateRenderer renderer = null;
    
    private static class DefaultTemplateRenderer implements TemplateRenderer {
        @SuppressWarnings("unchecked")
        public Object render(Object value) {
            if (value instanceof Renderable) {
                return ((Renderable)value).render(this);
            } else if (value instanceof Iterable) {
                List ls = new ArrayList();
                for (Object item : (Iterable)value) {
                    ls.add(render(item));
                }
                return ls;
            } else {
                return value;
            }
        }    
    };
    
    public TemplateContext() {
        this.renderer = new DefaultTemplateRenderer();
    }

    public TemplateContext(TemplateRenderer renderer) {
        this.renderer = renderer;
    }

    public TemplateContext(TemplateLookup lookup) {
        this.lookup = lookup;
        this.renderer = new DefaultTemplateRenderer();
    }

    public TemplateContext(TemplateRenderer renderer, TemplateLookup lookup) {
        this.renderer = renderer;
        this.lookup = lookup;
    }

    protected TemplateContext(TemplateContext parent) {
        this.parent = parent;
        this.lookup = parent.lookup;
        this.renderer = parent.renderer;
    }
    
    public TemplateContext child() {
        return new TemplateContext(this);
    }

    public Object get(String name) {
        if (model.containsKey(name)) {
            return model.get(name);
        }

        if (parent != null) {
            return parent.get(name);
        } else {
            return null;
        }
    }

    public TemplateContext set(String name, Object value) {
        model.put(name, renderer.render(value));
        return this;
    }
    
    public TemplateContext tile(String name, Template template) {
        return child().set(name, new TemplateSection(template, this));
    }
    
    public void render(Writer out, Template template) throws IOException {
        template.print(out, this);
    }

    public Template find(String path) {
        return lookup.find(path);
    }
}