package com.licenta.page;

import com.licenta.core.CommandLaunch;
import com.licenta.dao.beans.UserBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public abstract class LaunchCommandPageUI {
    private List<WebElement> elements;
    protected String commandName;
    protected CommandLaunch commandLaunch;

    protected LaunchCommandPageUI(String commandName, CommandLaunch commandLaunch) {
        this.commandName = commandName;
        this.commandLaunch = commandLaunch.doClone();
        this.elements = new ArrayList<>();
        this.buildPageUI();
    }

    public String getContent(String csrfToken) {
        String encodedCommandName;
        try {
            encodedCommandName = URLEncoder.encode(this.commandName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodedCommandName = this.commandName;
        }
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<div class=\"container\">\n");
        contentBuilder.append("<h2 class=\"page-header\">").append(this.commandName).append("</h2>\n");
        contentBuilder.append("<form id=\"form\" action=\"upload\" method=\"post\" enctype=\"multipart/form-data\" class=\"form-horizontal\" role=\"form\">\n");
        contentBuilder.append("<input type=\"hidden\" name=\"commandName\" value=\"").append(encodedCommandName).append("\">");
        contentBuilder.append("<input type=\"hidden\" name=\"CSRFtoken\" value=\"").append(csrfToken).append("\">");
        for (WebElement currentElement : elements) {
            contentBuilder.append(currentElement.buildHtmlElement());
            contentBuilder.append("</br></br>\n");
        }

        contentBuilder.append("<div class=\"form-group\">\n");
        contentBuilder.append("<div class=\"col-sm-offset-2 col-sm-10\">\n");
        contentBuilder.append("<button type=\"submit\" class=\"btn btn-default\">Submit</button>\n");
        contentBuilder.append("</div>\n");
        contentBuilder.append("</div>\n");
        contentBuilder.append("</form>\n");
        contentBuilder.append("</div>\n");
        return new String(contentBuilder);
    }

    protected void addWebElement(WebElement webElement) {
        this.elements.add(webElement);
    }

    public abstract void buildPageUI();

    public String validateParameters(Map<String, String> parametersMap) {
        String invalidParams = "";
        for(WebElement element : elements) {
            if(!element.verifyContent(parametersMap)) invalidParams+=element.name + " , ";
        }

        return invalidParams;
    }

    public void launchCommand(Map<String, String> parametersMap, UserBean user, String workingDirectory) {
        this.commandLaunch.launchCommand(parametersMap, user,this.commandName,workingDirectory);
    }

    public String toString() {
        return this.commandName;
    }

    public void cancel() {
        this.commandLaunch.cancelLaunchedCommand();
    }

    public abstract LaunchCommandPageUI doClone();
}
