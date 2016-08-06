<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="/Webmcc/">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
    <script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="col-md-12">
                <div class="error-template">
                    <h1>
                        Oops!</h1>
                    <h2>
                        ${pageContext.errorData.statusCode}</h2>
                    <div class="error-details">
                        Requested page : ${pageContext.errorData.requestURI}
                        <!--${pageContext.exception}-->
                    </div>
                    <div class="error-actions">
                        <a href="index.jsp" class="btn btn-primary btn-lg">
                            Take Me Home
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
