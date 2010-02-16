package com.codegremlins.lemonjuice.engine.builtins;

import java.net.URLEncoder;

import com.codegremlins.lemonjuice.TemplateContext;
import com.codegremlins.lemonjuice.engine.Element;

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
