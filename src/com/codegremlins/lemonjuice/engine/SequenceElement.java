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
import java.util.List;

import com.codegremlins.lemonjuice.TemplateContext;

class SequenceElement extends Element {
    private Element[] elements;

    public SequenceElement(List<Element> elements) {
    	compress(elements);
        this.elements = elements.toArray(new Element[elements.size()]);
    }
    
    private void compress(List<Element> elements) {
    	int counter = 0;
    	for (;;) {
    		if (counter >= elements.size()) {
    			return;
    		}
    		
	    	if (isCompressable(elements, counter + 1)) { 
	    		if (isRemovable(elements, counter)) {
	    			elements.remove(counter);
	    		}
	    	}

	    	counter++;
    	}
    }

    private boolean isCompressable(List<Element> elements, int counter) {
    	if (counter == -1) {
    		return true;
    	}
    	
    	if (counter >= elements.size()) {
    		return false;
    	}
    	
    	Element element = elements.get(counter);
    	return element instanceof DefineElement || element instanceof SetElement;
    }
    
    private boolean isRemovable(List<Element> elements, int counter) {
    	if (counter >= elements.size()) {
    		return false;
    	}

    	Element element = elements.get(counter);
    	if (element instanceof TextElement) {
    		String text = ((TextElement)element).getValue().trim();
    		return text.length() == 0;
    	} else {
    		return false;
    	}
    }

    public void print(Writer out, TemplateContext model) throws Exception {
        for (Element v : elements) {
            v.print(out, (TemplateContext)model);
        }
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        Object last = null;
        for (Element v : elements) {
            last = v.evaluate((TemplateContext)model);
        }
        
        return last;
    }

    public Element[] getElements() {
        return elements;
    }
}