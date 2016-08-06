<%@ page import="com.licenta.core.executor.QueuedCommandExecutor" %>
<%@ page import="com.licenta.dao.beans.UserBean" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
    QueuedCommandExecutor executor = (QueuedCommandExecutor) request.getServletContext().getAttribute("executor");

    Map<String,String> runningCommands = executor.getRunningCommands(loggedUser.getId());
    for(Map.Entry<String,String> entry : runningCommands.entrySet()) {
        out.println("<div class=\"col-sm-10\">\n" +
                "            <div class=\"progress\">\n" +
                "                <div id = \""+entry.getKey()+"\" class=\"progress-bar progress-bar-striped active\" role=\"progressbar\" style=\"width:100%\">\n" +
                "                " + entry.getValue() +"<b>[" + entry.getKey() + "]</b>" + "</div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-sm-2\">\n" +
                "            <button class=\"btn btn-primary btn-danger cancelButton\" onclick=\"cancelExecution('"+ entry.getKey() + "','"+ session.getAttribute("CSRFToken") + "');\">x</button>\n" +
                "        </div>");
    }
%>