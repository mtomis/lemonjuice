package com.codegremlins.lemonjuice.engine;

import java.io.PrintWriter;
import java.io.Writer;

public class JavascriptTemplateVisitor implements TemplateElementVisitor {
    private PrintWriter out;
    
    public JavascriptTemplateVisitor(Writer out) {
        if (out instanceof PrintWriter) {
            this.out = (PrintWriter)out;
        } else {
            this.out = new PrintWriter(out);
        }
        
        init(this.out);
    }
    
    private void init(PrintWriter out) {
        out.println("function _true(value) {return value != null && value != undef && value != false;}");
    }
    
    public Object visit(AndElement element) throws Exception {
        // out.print("(");
        //out.print("_true("
        // item.visit(this);
        //out.print(")"
        // out.print(")");
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ApplyElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ConstantElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(DefineElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ForElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ForInlineElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(FunctionElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(IfElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(IncludeElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(IndirectPropertyElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(InlineElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ListElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(MapElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(NotElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(OrElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(PropertyElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(SequenceElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(SetElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(TextElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object visit(ValueElement element) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}