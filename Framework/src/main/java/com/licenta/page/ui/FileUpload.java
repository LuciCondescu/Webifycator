package com.licenta.page.ui;

import com.licenta.page.Verifier;
import com.licenta.page.WebElement;

/**
 * Created by Lucian CONDESCU
 */
public class FileUpload extends WebElement {

    public FileUpload(String name, Verifier verifier) {
        super(name, verifier, null);
    }

    public FileUpload(String name, String description) {
        super(name, null, description);
    }

    public FileUpload(String name, Verifier verifier, String description) {
        super(name, verifier, description);
    }
    @Override
    public StringBuilder buildElement() {
        StringBuilder builder = new StringBuilder();
        String elementID = this.name.replace(" ","");
        builder.append("<div id= \"").append(this.toString()).append("\" class=\"form-group\">\n");
        builder.append("<label class=\"control-label col-sm-2\">").append(this.name).append("</label>\n");
        builder.append("<div class=\"col-sm-10\">\n");
        builder.append("<div style=\"position:relative;\">\n");
        builder.append("<a class='btn btn-default'>");
        builder.append("Choose File...");
        builder.append("<input type=\"file\" class=\"btn-file\" name=\"file_source_").append(super.name).append("\" size=\"40\"  onchange='$(\"#upload-file").append(elementID).append("\").html($(this).val());'>");
        builder.append("</a>\n");
        builder.append("&nbsp;\n");
        builder.append("<span class='label label-default' id=\"upload-file").append(elementID).append("\"></span>\n");
        builder.append("</div>");
        if(this.description != null) builder.append(description);
        builder.append("</div>");
        builder.append("</div>");

        return builder;
    }
}
