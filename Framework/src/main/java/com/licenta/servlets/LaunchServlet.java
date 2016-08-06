package com.licenta.servlets;

import com.licenta.core.executor.QueuedCommandExecutor;
import com.licenta.core.executor.RunnableCommandLaunch;
import com.licenta.dao.beans.UserBean;
import com.licenta.page.CommandPageFactory;
import com.licenta.page.LaunchCommandPageUI;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
@WebServlet(name = "LaunchServlet",
            urlPatterns = {"/protected/launch"})
public class LaunchServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String receivedToken = request.getParameter("CSRFtoken");
        String storedToken = (String) request.getSession().getAttribute("CSRFToken");
        if(!storedToken.equals(receivedToken))  {
            System.out.println("storedToken = " + storedToken);
            System.out.println("receivedToken = " + receivedToken);
            return;
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();

        String commandName = URLDecoder.decode(request.getParameter("commandName"),"UTF-8");
        Enumeration<String> parametersNames = request.getParameterNames();
        Map<String,String> parametersMap = new HashMap<>();
        final Enumeration<String> attributeNames = request.getAttributeNames();

        while (parametersNames.hasMoreElements()) {
            String parameterName = parametersNames.nextElement();
            if (!parameterName.equals("token") && !parameterName.equals("commandName") && !parameterName.startsWith("file_source")) {
                parametersMap.put(parameterName, request.getParameter(parameterName));
            }
        }

        String workingDirectory = (String) request.getAttribute("dir");

        if ("".equals(workingDirectory)) {
            workingDirectory = System.getProperty("user.home");
        }

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            if(!attributeName.startsWith("javax.servlet") && !attributeName.startsWith("org.") && !attributeName.startsWith("dir"))
                parametersMap.put(attributeName, (String) request.getAttribute(attributeName));
        }

        System.out.println("parametersMap = " + parametersMap);
        LaunchCommandPageUI commandPageUI = null;
        CommandPageFactory commandPageFactory = (CommandPageFactory) request.getServletContext().getAttribute("factory");
        if (commandName != null) commandPageUI = commandPageFactory.getCommand(commandName);
        String invalidParameters = commandPageUI.validateParameters(parametersMap);
        String data;
        boolean error = false;
        if(!"".equals(invalidParameters))  {
            data = "Input validation failed. Please review input(s) :[ "+invalidParameters.substring(0,invalidParameters.length() -2)+" ]";
            error = true;
            FileUtils.deleteQuietly(new File(workingDirectory));
        } else {
            UserBean loggedInUser = (UserBean) request.getSession().getAttribute("loggedUser");
            QueuedCommandExecutor executor = (QueuedCommandExecutor) request.getServletContext().getAttribute("executor");
            data  = executor.submitCommand(new RunnableCommandLaunch(commandPageUI, parametersMap, loggedInUser,workingDirectory),loggedInUser.getId());
            if(!data.startsWith("Command")) error = true;
        }

        jsonObject.put("data",data);
        jsonObject.put("error",error);
        System.out.println("jsonObject = " + jsonObject);
        out.write(jsonObject.toString());
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
