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

    public abstract StringBuilder buildElement();

    public String toString() {
        return this.name.toLowerCase().replace(" ","-");
    }
}
