package com.licenta.filters;

import com.licenta.core.executor.QueuedCommandExecutor;
import com.licenta.dao.beans.UserBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Lucian CONDESCU
 */
@WebFilter("/protected/*")
public class CheckLoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("loggedUser");
        QueuedCommandExecutor executor = (QueuedCommandExecutor) request.getServletContext().getAttribute("executor");
        if (userBean != null && userBean.getId() != null && executor != null) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }

}
