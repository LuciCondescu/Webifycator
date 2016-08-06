<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="homePage.jsp"><%out.println(application.getAttribute("name"));%></a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="homePage.jsp">Home</a></li>
        <li><a href="../logout.jsp">Logout</a></li>
      </ul>
    </div>
  </div>
</nav>
<br>