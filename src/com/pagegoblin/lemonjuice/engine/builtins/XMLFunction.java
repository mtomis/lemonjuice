package com.pagegoblin.lemonjuice.engine.builtins;

import com.pagegoblin.lemonjuice.TemplateContext;
import com.pagegoblin.lemonjuice.engine.Element;
import com.pagegoblin.lemonjuice.util.XMLEscape;

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
