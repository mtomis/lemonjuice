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

package com.pagegoblin.lemonjuice.engine.builtins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pagegoblin.lemonjuice.TemplateContext;
import com.pagegoblin.lemonjuice.engine.Element;
import com.pagegoblin.lemonjuice.engine.FunctionElement;

@SuppressWarnings("unchecked")
public class ReverseFunction extends FunctionElement {
    public ReverseFunction(Element[] parameters) {
        super(parameters);
    }
    
    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        List list = list(model, 0);
        
        if (list != null) {
            List ls = new ArrayList(list);
            Collections.reverse(ls);
            return ls;
        } else {
            return null;
        }
    }
}