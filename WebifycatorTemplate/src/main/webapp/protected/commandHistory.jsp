<%@ page import="com.licenta.dao.CommandDAO" %>
<%@ page import="com.licenta.dao.beans.CommandBean" %>
<%@ page import="com.licenta.dao.beans.UserBean" %>
<%@ page import="com.licenta.dao.impl.JDBCCommandDAOImpl" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
    CommandDAO commandDAO = new JDBCCommandDAOImpl();
    List<CommandBean> commandBeanList = commandDAO.getUserCommands(loggedUser.getId());
    if(commandBeanList == null || commandBeanList.size() < 1) {
        out.println("<p class=\"noRecord\">No records found in database</p>");
    } else {
        out.println("<div class=\"list-group\"></div>");
        for (CommandBean command : commandBeanList) {
            out.println("<a href=\"commandDetails.jsp?id=" + command.getCommandID() + "\" class=\"list-group-item\"  target=\"_blank\"><b>" + StringEscapeUtils.escapeHtml(command.getCommandName()) + "#" + command.getCommandID()+ "</b> [" + StringEscapeUtils.escapeHtml(command.getTimestamp().toString()) + "]</a>");
        }
    }
%>