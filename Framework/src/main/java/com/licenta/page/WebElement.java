package com.licenta.page;

import java.util.Map;

/**
 * @author  Lucian CONDESCU
 */
public abstract class WebElement {
    protected String name;
    private Verifier verifier;
    protected String description;

    public WebElement(String name, Verifier verifier, String description) {
        this.name = name;
        this.verifier = verifier;
        this.description = description;
    }

    public boolean verifyContent(Map<String, String> parametersMap) {
        final String actualValue = parametersMap.get(this.name);
        return this.verifier == null || this.verifier.verify(actualValue);
    }
    
    public StringBuilder buildHtmlElement() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div id = \"").append(this.toString()).append("\" class=\"form-group\">\n");
        builder.append("<label class=\"control-label col-sm-2\">").append(this.name).append("</label>\n");
        builder.append("<div class=\"col-sm-10\">\n");
        this.buildElement(builder);
        builder.append("</div>");
        builder.append("</div>");
        return builder;
    }

    protected abstract StringBuilder buildElement(StringBuilder builder);

    public String toString() {
        return this.name.toLowerCase().replace(" ","-");
    }
}
