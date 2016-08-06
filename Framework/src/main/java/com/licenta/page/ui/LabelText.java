package com.licenta.page.ui;

import com.licenta.page.WebElement;

/**
 * @author Lucian CONDESCU
 */
public class LabelText extends WebElement {

    private String info;

    public LabelText(String name, String info) {
        super(name,null, null);
        this.info = info;
    }

    @Override
    public StringBuilder buildElement() {
        StringBuilder builder = new StringBuilder();

        builder.append("<div id = \"").append(this.toString()).append("\" class=\"form-group\">\n");
        builder.append("<label class=\"control-label col-sm-2\">").append(this.name).append("</label>\n");
        builder.append("<div class=\"col-sm-10\">\n");
        builder.append("<span class=\"control-label label label-default\">").append(this.info).append("</span>\n");
        builder.append("</div>\n");
        builder.append("</div>\n");

        return builder;
    }
}
