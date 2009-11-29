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

package com.pagegoblin.lemonjuice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import com.pagegoblin.lemonjuice.property.CopyOnWriteHashMap;

public final class DefaultTemplateLookup implements TemplateLookup {
    public static final DefaultTemplateLookup INSTANCE = new DefaultTemplateLookup();

    private static final Template MISSING = new Template(); 

    private final static Map<String, Template> templates = new CopyOnWriteHashMap<String, Template>(); 
    
    public Template find(String name) {
        try {
            Template template;
            
            if (templates.containsKey(name)) {
                template = templates.get(name);
            } else {
                InputStream in = loadResource(name);
                if (in == null) {
                    templates.put(name, MISSING);
                    return null;
                }
                
                template = new Template(name, in);
                
                int index = name.lastIndexOf('/');
                if (index != -1) {
                    template.setLocation(name.substring(0, index + 1));
                }
                
                templates.put(name, template);
            }
    
            if (template == MISSING) {
                return null;
            }
            
            return template;
        } catch (IOException ex) {
            synchronized (templates) {
                templates.put(name, MISSING);
            }
            
            throw new TemplateException(ex.getMessage());
        }
    }

    protected InputStream loadResource(String name) throws IOException {
        URL url = DefaultTemplateLookup.class.getClassLoader().getResource(name);
        if (url != null) {
            return url.openStream();
        } else {
            File file = new File(name);
            
            if (!file.exists()) {
                return null;
            }
            return new FileInputStream(file);
        }
    }
}