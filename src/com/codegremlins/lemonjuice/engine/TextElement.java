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

import java.io.IOException;
import java.io.Writer;

import com.codegremlins.lemonjuice.TemplateContext;

class TextElement extends Element {
    private String value;

    public TextElement(String value) {
        this.value = value;
    }

    public void print(Writer out, TemplateContext model) throws IOException {
        out.write(value);
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        return value;
    }

    @Override
    public void visit(TemplateElementVisitor context) throws Exception {
        context.visit(this);
    }

	public String getValue() {
		return value;
	}
}