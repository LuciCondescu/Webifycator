package com.licenta.page.ui;

import com.licenta.page.Verifier;
import com.licenta.page.WebElement;

/**
 * Created by Lucian CONDESCU
 */
public class InputText extends WebElement {

    public InputText(String name,Verifier verifier) {
        super(name,verifier, null);
    }

    public InputText(String name, String description) {
        super(name,null,description);
    }

    public InputText(String name, Verifier verifier, String description) {
        super(name,verifier,description);
    }
    @Override
    public StringBuilder buildElement(StringBuilder builder) {
        builder.append("<input type=\"text\" class=\"form-control\" name=\"").append(this.name).append("\" placeholder=\"Enter ").append(this.name).append(" here...\">\n");
        if(this.description != null ) builder.append(description);
        return builder;
    }
}
