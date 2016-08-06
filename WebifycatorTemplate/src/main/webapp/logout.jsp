<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.setAttribute("loggedUser",null);
    session.invalidate();
    response.sendRedirect(request.getContextPath() + "/index.jsp");
%>
