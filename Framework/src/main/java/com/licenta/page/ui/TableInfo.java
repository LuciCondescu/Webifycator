package com.licenta.page.ui;

import com.licenta.page.WebElement;

import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public class TableInfo extends WebElement {

    private List<String> head;
    private List<List<String>> content;

    public TableInfo(String name, List<String> head, List<List<String>> content) {
        super(name, null, null);
        this.head = head;
        this.content = content;
    }

    public TableInfo(String name, List<String> head, List<List<String>> content, String description) {
        super(name, null, description);
        this.head = head;
        this.content = content;
    }

    @Override
    public StringBuilder buildElement(StringBuilder builder) {
        builder.append("<table class=\"table table-bordered\">\n");
        builder.append("<thead>\n<tr>\n");

        for (String name : head) {
            builder.append("<th>").append(name).append("</th>");
        }
        builder.append("</tr>\n</thead>\n");
        builder.append("<tbody>\n");
        for (List<String> row : content) {
            builder.append("<tr>");
            for (String entry : row) {
                builder.append("<td>").append(entry).append("</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</tbody>\n</table>");
        if(this.description != null ) builder.append(description);

        return builder;
    }
}
