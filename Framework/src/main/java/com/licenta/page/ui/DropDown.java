package com.licenta.page.ui;

import com.licenta.page.Verifier;
import com.licenta.page.WebElement;

import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public class DropDown extends WebElement {

    private List<String> options;

    public DropDown(String name, Verifier verifier, List<String> options) {
        super(name, verifier, null);
        this.options = options;
    }

    public DropDown(String name, List<String> options, String description) {
        super(name, null, description);
        this.options = options;
    }

    public DropDown(String name, Verifier verifier, List<String> options, String description) {
        super(name, verifier, description);
        this.options = options;
    }

    @Override
    public StringBuilder buildElement() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div id = \"").append(this.toString()).append("\" class=\"form-group\">\n");
        builder.append("<label class=\"control-label col-sm-2\">").append(this.name).append("</label>\n");
        builder.append("<div class=\"col-sm-10\">\n");
        builder.append("<select class=\"form-control\" name=\"").append(this.name).append("\">\n");
        if (options != null) {
            for (String option : options) {
                builder.append("<option value=\"").append(option).append("\">").append(option).append("</option>\n");
            }
        }
        builder.append("</select>\n");
        if(this.description != null) builder.append(description);
        builder.append("</div>\n");
        builder.append("</div>\n");
        return builder;
    }
}
