package com.licenta.servlets;

import com.licenta.oauth.OauthUser;
import com.licenta.oauth.Provider;
import com.licenta.oauth.ProviderFactory;
import org.scribe.builder.ServiceBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Lucian CONDESCU
 */
@WebServlet(name = "LoginServlet",
            urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String providerString = request.getParameter("provider");
        if(providerString == null || "".equals(providerString)) {
            session.invalidate();
            return;
        }

        providerString = providerString.trim().toLowerCase();

        Provider provider = ProviderFactory.getProviderByName(providerString, request.getServletContext());
        OauthUser user = new OauthUser();
        user.setService(provider.getOauthService(new ServiceBuilder()));
        user.setRequest(provider.getOauthRequest());
        session.setAttribute("oauthUser",user);
        response.sendRedirect(user.getService().getAuthorizationUrl(null));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
