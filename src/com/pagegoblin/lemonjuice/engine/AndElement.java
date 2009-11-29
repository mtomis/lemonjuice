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

import com.pagegoblin.lemonjuice.TemplateContext;

class AndElement extends Element {
    private Element[] parameters;

    public AndElement(Element[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        for (Element element : parameters) {
            Object value = element.evaluate(model);
            if (value == null || value.equals(false)) {
                return null;
            }
        }
        
        return true;
    }
}