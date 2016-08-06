package com.licenta.servlets;


import com.licenta.dao.UserDAO;
import com.licenta.dao.beans.UserBean;
import com.licenta.dao.impl.JDBCUserDAOImpl;
import com.licenta.oauth.OauthUser;
import com.licenta.utils.TempGenerator;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *@author Lucian CONDESCU
 */
@WebServlet(name = "Oauth2Callback",
            urlPatterns = {"/oauth2Callback"})
public class Oauth2Callback extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String error = request.getParameter("error");
        if (error != null && "access_denied".equals(error.trim())) {
            session.invalidate();
            response.sendRedirect(request.getContextPath());
            return;
        }

        OauthUser user = (OauthUser) session.getAttribute("oauthUser");
        OAuthService service = user.getService();
        OAuthRequest oAuthRequest = user.getRequest();

        String code = request.getParameter("code");
        Token token = service.getAccessToken(null, new Verifier(code));
        System.out.println("Token is :" + token);
        service.signRequest(token, oAuthRequest);
        Response res = oAuthRequest.send();
        JsonReader reader = Json.createReader(new ByteArrayInputStream(res.getBody().getBytes()));
        JsonObject client = reader.readObject();
        System.out.println("client = " + client);
        UserDAO userDAO = new JDBCUserDAOImpl();
        final String userId = client.getString("id");
        final String userName = client.getString("name");
        final String email = client.getString("email");
        if (userId != null && userName != null) {
            final UserBean loggedInUser = new UserBean(userId, userName, email);
            session.setAttribute("loggedUser", loggedInUser);
            if (!userDAO.checkUser(loggedInUser.getId()))
                userDAO.addUser(loggedInUser);
            session.setAttribute("CSRFToken", TempGenerator.getInstance().nextTemp());
            response.sendRedirect(request.getContextPath() + "/protected/homePage.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}