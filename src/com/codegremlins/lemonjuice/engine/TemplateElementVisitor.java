package com.codegremlins.lemonjuice.engine;

public interface TemplateElementVisitor {
    public Object visit(AndElement element) throws Exception;
    public Object visit(ApplyElement element) throws Exception;
    public Object visit(ConstantElement element) throws Exception;
    public Object visit(DefineElement element) throws Exception;
    public Object visit(ForElement element) throws Exception;
    public Object visit(ForInlineElement element) throws Exception;
    public Object visit(FunctionElement element) throws Exception;
    public Object visit(IfElement element) throws Exception;
    public Object visit(IncludeElement element) throws Exception;
    public Object visit(IndirectPropertyElement element) throws Exception;
    public Object visit(InlineElement element) throws Exception;
    public Object visit(ListElement element) throws Exception;
    public Object visit(MapElement element) throws Exception;
    public Object visit(NotElement element) throws Exception;
    public Object visit(OrElement element) throws Exception;
    public Object visit(PropertyElement element) throws Exception;
    public Object visit(SequenceElement element) throws Exception;
    public Object visit(SetElement element) throws Exception;
    public Object visit(TextElement element) throws Exception;
    public Object visit(ValueElement element) throws Exception;
    public Object visit(EqualElement element) throws Exception;
}
