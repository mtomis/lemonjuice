/*
 *  lemonjuice - Java Template Engine.
 *  Copyright (C) 2009-2011 Manuel Tomis manuel@codegremlins.com
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
import java.io.Reader;

class PeekReader {
    private Reader in;
    private boolean cached = false;
    private int value;
    private int line = 1;
    private int column = 1;
    
    public PeekReader(Reader in) {
        this.in = in;
    }
    
    public PeekReader(Reader in, int line) {
        this.in = in;
        this.line = line;
    }

    private int next() throws IOException {
        int c = in.read();
        if (c == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        
        return c;
    }

    public int read() throws IOException {
        if (cached) {
            cached = false;
            return value;
        } else {
            return next();
        }
    }

    public int peek() throws IOException {
        if (!cached) {
            cached = true;
            value = next();
        }
        return value;
    }
    
    public int line() {
        return line;
    }

    public int column() {
        return column;
    }
}