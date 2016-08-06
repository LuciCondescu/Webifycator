<%@ page import="com.licenta.dao.CommandDAO" %>
<%@ page import="com.licenta.dao.beans.CommandBean" %>
<%@ page import="com.licenta.dao.beans.UserBean" %>
<%@ page import="com.licenta.dao.impl.JDBCCommandDAOImpl" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../assets/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" flush="true"/>
    <br>
    <div class="container">
        <%
            UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
            String commandID = request.getParameter("id");
            if(commandID == null) {
                out.println("<br><p class=\"noRecord\">Invalid request</p>");
            } else {
                CommandDAO commandDAO = new JDBCCommandDAOImpl();
                CommandBean commandBean = commandDAO.getCommand(Long.parseLong(commandID));
                if(commandBean == null) {
                    out.println("<br><p class=\"noRecord\">The requested command was not found in database.</p>");
                } else if(!commandBean.getUserID().equals(loggedUser.getId())) {
                    out.println("<br><p class=\"noRecord\">You are not authorized to see this page !</p>");
                } else {
                    out.println("<h1></h1><h1>" + StringEscapeUtils.escapeHtml(commandBean.getCommandName()) + "#" + commandBean.getCommandID() + "</h1>\n" +
                            "    <div class=\"jumbotron\">\n" +
                            "        <h2>Result</h2>\n" +
                            "        ");
                  out.println("<pre>" + StringEscapeUtils.escapeHtml(commandBean.getResult()) + "</pre>");
                    out.println("</div>");
                    out.println("<div class=\"jumbotron\">\n" +
                            "        <h2>Details</h2>");
                    if(commandBean.getStandardOutput()!=null) out.println("<pre><b>Standard output :</b> " +StringEscapeUtils.escapeHtml(commandBean.getStandardOutput()) + "</pre>");
                    if(commandBean.getStandardError()!=null) out.println("<pre><b>Standard error :</b> " +StringEscapeUtils.escapeHtml(commandBean.getStandardError()) + "</pre>");
                    out.println("<pre><b>Launched command :</b> " +StringEscapeUtils.escapeHtml(commandBean.getLaunchedCommand()) + "</pre>");
                    out.println("<pre><b>Command started at :</b> " +StringEscapeUtils.escapeHtml(commandBean.getTimestamp().toString()) + "</pre>");
                    out.println("</div>");
                }
            }
        %>
    </div>
</body>
</html>