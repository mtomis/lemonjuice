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

package com.codegremlins.lemonjuice.engine.builtins;

import java.util.ArrayList;
import java.util.List;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.FunctionElement;

@SuppressWarnings("unchecked")
public class SplitFunction extends FunctionElement {
    public SplitFunction(Element[] parameters) {
        super(parameters);
    }
    
    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        List list = list(model, 0);
        int number = (int)number(model, 1);

        List ls = new ArrayList();
        
        if (list != null) {
            if (number < 1) {
                number = 1;
            }

            int start = 0;
            for (;;) {
                int end = start + number;
                if (end <= list.size()) {
                    ls.add(list.subList(start, end));
                    start += number;
                } else {
                    if (start < list.size()) {
                        ls.add(list.subList(start, list.size()));
                    }
                    return ls;
                }
            }
        } else {
            return null;
        }
    }
}