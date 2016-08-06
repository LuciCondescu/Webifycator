<%@ page import="com.licenta.dao.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-social.css">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/docs.css">
    <link rel="stylesheet" type="text/css" href="assets/css/signin.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
</head>
<% UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
    if(loggedUser != null) response.sendRedirect(request.getContextPath() + "/protected/homePage.jsp");

    String applicationName = (String) application.getAttribute("name");
     String[] providers = (String[]) application.getAttribute("providers");
%>
<body>
<%if(applicationName != null) out.println("<h3 class=\"text-center\">" + applicationName + "</h3>" );%>
  <div class="container">
      <form class="form-signin">
              <h3 class="form-signin-heading text-center">Login to your Dashboard</h3>
              <% for (String provider : providers) {
                  out.println("");
                  out.println("<a href=\"login?provider=" + provider + "\" class=\"btn btn-block btn-social btn-lg btn-" + provider + "\">");
                  out.println("<i class=\"fa fa-" + provider + "\"></i> Sign in with " + provider.substring(0, 1).toUpperCase() + provider.substring(1));
                  out.println("</a>");
              }%>
      </form>
  </div>
</body>
</html>
