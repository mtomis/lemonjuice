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

package com.codegremlins.lemonjuice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.Parser;

public class Template {
    private final Element element;
    private String[] parameters;
    private String location;
    private String name;
    private Element[] defaults;
    
    public Template() {
        element = null;
    }
    
    public Template(Element element, String[] parameters, Element[] defaults) {
        this.element = element;
        this.parameters = parameters;
        this.defaults = defaults;
    }
    
    public void setDefaults(String[] parameters, Element[] defaults) {
        this.parameters = parameters;
        this.defaults = defaults;
    }

    public Template(String in) throws IOException {
        Parser p = new Parser(new StringReader(in), this);
        element = p.parse();
    }

    public Template(InputStream in) throws IOException {
        Parser p = new Parser(new InputStreamReader(in), this);
        element = p.parse();
    }

    public Template(String name, InputStream in) throws IOException {
        this.name = name;
        Parser p = new Parser(new InputStreamReader(in), this);
        element = p.parse();
    }

    public Template(Reader in) throws IOException {
        Parser p = new Parser(in, this);
        element = p.parse();
    }
    
    public Template(String name, Reader in) throws IOException {
        this.name = name;
        Parser p = new Parser(in, this);
        element = p.parse();
    }

    public void print(Writer out, TemplateContext model) throws IOException {
        try {
            element.print(out, model);
        } catch (IOException ex) {
            throw ex;
        } catch (TemplateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TemplateException(ex.getMessage(), ex);
        }
    }

    public Object evaluate(TemplateContext model) throws IOException {
        try {
            return element.evaluate(model);
        } catch (IOException ex) {
            throw ex;
        } catch (TemplateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TemplateException(ex.getMessage(), ex);
        }
    }    

    public String[] getParameters() {
        return parameters;
    }

    public String getLocation() {
        if (location == null) {
            return "<template>";
        } else {
            return location;
        }
    }

    public String getName() {
        if (name == null) {
            return "<template>";
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public Element[] getDefaults() {
        return defaults;
    }
}