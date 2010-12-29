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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.engine.FunctionElement;

public class RangeFunction extends FunctionElement {
    public RangeFunction(Element[] parameters) {
        super(parameters);
    }
    
    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        final long start = number(model, 0);
        final long finish = number(model, 1);
        
        return new List() {
            public boolean add(Object arg0) {
                return false;
            }

            public void add(int arg0, Object arg1) {
            }

            public boolean addAll(Collection arg0) {
                return false;
            }

            public boolean addAll(int arg0, Collection arg1) {
                return false;
            }

            public void clear() {
            }

            public boolean contains(Object arg0) {
                return false;
            }

            public boolean containsAll(Collection arg0) {
                return false;
            }

            public Object get(int i) {
                return start + i;
            }

            public int indexOf(Object n) {
                return 0;
            }

            public boolean isEmpty() {
                return false;
            }

            public Iterator iterator() {
                return null;
            }

            public int lastIndexOf(Object arg0) {
                return 0;
            }

            public ListIterator listIterator() {
                  return new ListIterator() {
                  private long current = start;
                  
                  public boolean hasNext() {
                      return current < finish;
                  }
      
                  public Object next() {
                      return current++;
                  }
      
                  public void remove() {
                  }

                public void add(Object arg0) {
                }

                public boolean hasPrevious() {
                    return false;
                }

                public int nextIndex() {
                    return 0;
                }

                public Object previous() {
                    return null;
                }

                public int previousIndex() {
                    return 0;
                }

                public void set(Object arg0) {
                }
              };
            }

            public ListIterator listIterator(int arg0) {
                return null;
            }

            public boolean remove(Object arg0) {
                return false;
            }

            public Object remove(int arg0) {
                return null;
            }

            public boolean removeAll(Collection arg0) {
                return false;
            }

            public boolean retainAll(Collection arg0) {
                return false;
            }

            public Object set(int arg0, Object arg1) {
                return null;
            }

            public int size() {
                return (int)(finish - start);
            }

            public List subList(int arg0, int arg1) {
                return null;
            }

            public Object[] toArray() {
                Object[] v = new Object[size()];
                for (int i = 0; i < v.length; i++) {
                    v[i] = start + i;
                }
                return v;
            }

            public Object[] toArray(Object[] arg0) {
                return null;
            }
        };
    }
}