package com.pagegoblin.lemonjuice.engine.builtins;

import java.net.URLEncoder;

import com.pagegoblin.lemonjuice.TemplateContext;
import com.pagegoblin.lemonjuice.engine.Element;

public class URLFunction extends AppendFunction {
    public URLFunction(Element[] parameters) {
        super(parameters);
    }

    @Override
    public Object evaluate(TemplateContext model) throws Exception {
        String text = (String)super.evaluate(model);
        
        return URLEncoder.encode(text, "utf-8");
    }
}
