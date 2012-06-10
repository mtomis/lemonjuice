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

import java.util.HashMap;
import java.util.Map;

import com.codegremlins.lemonjuice.TemplateContext;

@SuppressWarnings("unchecked")
class MapElement extends Element {
    private String[] keys;
    private Element[] values;

    public MapElement(String[] keys, Element[] values) {
        this.keys = keys;
        this.values = values;
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        Map map = new HashMap();
        
        for (int i = 0; i < values.length ; i++) {
            Element element = values[i];
            map.put(keys[i], element.evaluate(model));
        }
        
        return map;
    }
}