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

package com.codegremlins.lemonjuice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.codegremlins.lemonjuice.engine.JavascriptTemplateVisitor;

public final class Main {
    private TemplateContext context = new TemplateContext();
    private Settings settings = new Settings();
    
    public static void main(String[] args) {
        new Main().run(new ArrayList<String>(Arrays.asList(args)));
    }

    private void run(List<String> args) {
        settings.parse(args);
        runFiles(args);
    }
    
    private void runFiles(List<String> files) {
        for (String name : files) {
            File file = new File(name);
            if (!file.exists()) {
                System.err.println("WARNING: File `" + name + "' does not exist.");
            } else if (file.isDirectory()) {
                System.err.println("WARNING: File `" + name + "' is a directory.");
            } else {
                try {
                    runTemplate(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.err.print("ERROR: Cannot process `" + name + "':");
                    System.err.println(ex.getMessage());
                } catch (TemplateException ex) {
                    System.err.print("ERROR:");
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
    
    private void runTemplate(File file) throws IOException {
        Template template = new Template(file.getAbsolutePath(), new FileInputStream(file));
        String name = file.getAbsolutePath();
        int index = name.lastIndexOf('/');
        if (index != -1) {
            template.setLocation(name.substring(0, index + 1));
        }
        
        Writer out = new OutputStreamWriter(System.out);
        
        if (template != null) {
            if (settings.outputJavascript) {
                template.visit(new JavascriptTemplateVisitor(out));// print(out, context);
            } else {
                template.print(out, context);
            }
        }
        
        out.flush();
    }
    
    private static class Settings {
        public boolean outputJavascript = false;
        
        public void parse(List<String> args) {
            for (ListIterator<String> i = args.listIterator(); i.hasNext();) {
                String item = i.next().trim();
                if (item.startsWith("-")) {
                    if ("-js".equals(item) || "-javascript".equals(item)) {
                        outputJavascript = true;
                    }
                } else {
                    continue;
                }
                i.remove();
            }
        }
    }
}