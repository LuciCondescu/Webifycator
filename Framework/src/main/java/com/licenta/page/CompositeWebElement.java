package com.licenta.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public class CompositeWebElement extends WebElement {
    private List<WebElement> webElements;
    private MultipleVerifier multipleVerifier;

    public CompositeWebElement(String name, List<WebElement> webElements, MultipleVerifier multipleVerifier) {
        super(name,null, null);
        this.webElements = webElements;
        this.multipleVerifier = multipleVerifier;
    }

    @Override
    public StringBuilder buildElement(StringBuilder builder) {
        builder.append("<h3>").append(this.name).append("</h3>");
        for (WebElement webElement : webElements) {
            builder.append(webElement.buildHtmlElement()).append("\n");
            builder.append("</br></br>");
        }
        builder.append("</br>");
        return builder;
    }

    @Override
    public boolean verifyContent(Map<String, String> parametersMap) {
        Map<String,String> neededParams = new HashMap<>();
        for (WebElement webElement : webElements) {
            String webElementValue = parametersMap.get(webElement.name);
            neededParams.put(webElement.name,webElementValue);
        }
        return this.multipleVerifier == null || this.multipleVerifier.verify(neededParams);
    }
}
