<%@ page import="com.licenta.page.CommandPageFactory" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="../assets/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="../assets/css/dashboard.css">
  <link rel="stylesheet" type="text/css" href="../css/style.css">
  <script type="text/javascript" src="../assets/js/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="../assets/js/bootbox.min.js"></script>
  <script type="text/javascript" src="../assets/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/ajax.js"></script>
</head>
<body>
<jsp:include page="header.jsp" flush="true"/>
<div class="container-fluid">
  <div class="content">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul class="nav nav-sidebar">
        <li><a href="#" onclick="showOverview()">Overview</a></li>
        <li><a href="#" onclick="showHistory()">Command History</a></li>
      </ul>
      <ul class="nav nav-sidebar">
        <% CommandPageFactory commandPageFactory = (CommandPageFactory) application.getAttribute("factory");
          List<String> commandPages = commandPageFactory.getCommandsName();
          if(commandPages!=null) {
            for (String commandPage : commandPages) {
              out.println("<li><a href=\"command.jsp?page=" + URLEncoder.encode(commandPage,"UTF-8") + "\">" + commandPage + "</a></li>");
            }
          }
        %>
      </ul>
      <div id="liveCommands" class="content">

      </div>
    </div>
    <div id="mainContent" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

    </div>
  </div>
</div>
<script>
  $(document).ready(getRunningCommands());
  $(document).ready(showOverview());
</script>
</body>
</html>
