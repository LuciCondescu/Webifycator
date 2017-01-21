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
    public StringBuilder buildElement(StringBuilder builder) {
        builder.append("<span class=\"control-label label label-default\">").append(this.info).append("</span>\n");

        return builder;
    }
}
