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

package com.codegremlins.lemonjuice.engine.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.FunctionElement;

@SuppressWarnings("unchecked")
public class ShuffleFunction extends FunctionElement {
    public ShuffleFunction(Element[] parameters) {
        super(parameters);
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        if (parameters().length == 1) {
            Object v1 = parameters()[0].evaluate(model);
            if (v1 instanceof Collection) {
                List result = new ArrayList((Collection)v1);
                Collections.shuffle(result);
                return result;
            } else {
                return v1;
            }
        }
        
        return null;        
    }
}