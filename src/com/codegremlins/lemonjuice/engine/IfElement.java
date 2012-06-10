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

import com.codegremlins.lemonjuice.TemplateContext;

class IfElement extends Element {
    private Element condition;
    private Element trueValue;
    private Element falseValue;

    public IfElement(Element condition, Element trueValue, Element falseValue) {
        this.condition = condition;
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    @Override
    public void print(Writer out, TemplateContext model) throws Exception {
        Object value = condition.evaluate(model);
        if (value == null || Boolean.FALSE.equals(value)) {
            if (falseValue != null) {
                falseValue.print(out, model);
            }
        } else {
            trueValue.print(out, model);
        }
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        Object value = condition.evaluate(model);
        if (value == null) {
            if (falseValue != null) {
                return falseValue.evaluate(model);
            } else {
                return null;
            }
        } else {
            return trueValue.evaluate(model);
        }
    }
}