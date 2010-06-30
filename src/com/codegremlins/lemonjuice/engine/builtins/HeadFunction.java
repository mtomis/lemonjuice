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

import java.util.List;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.FunctionElement;

public class HeadFunction extends FunctionElement {
    public HeadFunction(Element[] parameters) {
        super(parameters);
    }
    
    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        List list = list(model, 0);
        long number = number(model, 1);

        if (list != null) {
            if (number < 1) {
                number = 1;
            }
            
            return list.subList(0, Math.min((int)number, list.size()));
        } else {
            return null; // fixme
        }
//        
//        if (parameters.length >= 1) {
//            Object v1 = parameters[0].evaluate(model);
//            if (v1 instanceof List) {
//                list = (List)v1;
//                
//                if (parameters.length >= 2) {
//                    try {
//                        number = Integer.parseInt("" + parameters[1].evaluate(model));
//                    } catch (Exception ex) {
//                    }
//
//                    if (number < 0) {
//                        number = 0;
//                    } else if (number > list.size()) {
//                        number = list.size();
//                    }
//                    
//                    if (type == Type.HEAD) {
//                        return list.subList(0, number);
//                    } else {
//                        return list.subList(number, list.size());
//                    }
//                }
//            } // TODO: this function is incpmplete
//        }
    }
}