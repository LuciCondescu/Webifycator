package com.licenta.servlets;

import com.licenta.core.executor.QueuedCommandExecutor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Lucian CONDESCU
 */
@WebServlet(name = "CancelTaskServlet",
            urlPatterns = {"/protected/cancel"})
public class CancelTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String receivedToken = request.getParameter("CSRFtoken");
        String storedToken = (String) request.getSession().getAttribute("CSRFToken");
        if(!storedToken.equals(receivedToken))  {
            System.out.println("storedToken = " + storedToken);
            System.out.println("receivedToken = " + receivedToken);
            return;
        }
        String idParameter = request.getParameter("hash");
        System.out.println("idParameter = " + idParameter);
        QueuedCommandExecutor executor = (QueuedCommandExecutor) request.getServletContext().getAttribute("executor");
        PrintWriter out = response.getWriter();
        try {
            int id = Integer.valueOf(idParameter);
            System.out.println("Trying to cancel command with id = " + id);
            executor.cancelCommand(id);
        } catch (NumberFormatException e) {
            out.println("Invalid request");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
