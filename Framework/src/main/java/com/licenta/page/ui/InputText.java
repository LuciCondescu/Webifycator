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
    public StringBuilder buildElement() {
        StringBuilder builder = new StringBuilder();

        builder.append("<div id = \"").append(this.toString()).append("\" class=\"form-group\">\n");
        builder.append("<label class=\"control-label col-sm-2\">").append(this.name).append("</label>\n");
        builder.append("<div class=\"col-sm-10\">\n");
        builder.append("<input type=\"text\" class=\"form-control\" name=\"").append(this.name).append("\" placeholder=\"Enter ").append(this.name).append(" here...\">\n");
        if(this.description != null ) builder.append(description);
        builder.append("</div>\n");
        builder.append("</div>\n");
        return builder;
    }
}
