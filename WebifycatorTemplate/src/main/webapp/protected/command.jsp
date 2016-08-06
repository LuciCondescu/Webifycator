<%@ page import="com.licenta.page.CommandPageFactory" %>
<%@ page import="com.licenta.page.LaunchCommandPageUI" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Command Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="../assets/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <script type="text/javascript" src="../assets/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../assets/js/jquery.form.js"></script>
    <script type="text/javascript" src="../assets/js/bootbox.min.js"></script>
    <script type="text/javascript" src="../js/formSubmit.js"></script>
</head>
<body>
    <jsp:include page="header.jsp" flush="true"/>
    <%  String commandPageName = URLDecoder.decode(request.getParameter("page"),"UTF-8");
        if(commandPageName != null) {
            CommandPageFactory commandPageFactory = (CommandPageFactory) application.getAttribute("factory");
            LaunchCommandPageUI commandPage = commandPageFactory.getCommand(commandPageName);
            if(commandPage != null) {
                final String csrfToken = (String) session.getAttribute("CSRFToken");
                out.println(commandPage.getContent(csrfToken));
            }
        }
    %>
</body>
</html>
