package com.codegremlins.lemonjuice.engine.builtins;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;
import com.codegremlins.lemonjuice.util.XMLEscape;

public class XMLFunction extends AppendFunction {
    public XMLFunction(Element[] parameters) {
        super(parameters);
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        String text = (String)super.evaluate(model);
        
        return XMLEscape.escape(text);
    }
}
