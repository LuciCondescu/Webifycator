package com.licenta.filters;

import com.licenta.core.executor.QueuedCommandExecutor;
import com.licenta.core.executor.RefuseCommandExecutor;
import com.licenta.exceptions.ConfigurationException;
import com.licenta.page.CommandPageFactory;
import com.licenta.utils.SendMail;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Lucian CONDESCU
 */
@WebListener
public class ServletContext implements ServletContextListener {
    private QueuedCommandExecutor executor;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        final javax.servlet.ServletContext application = servletContextEvent.getServletContext();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/appConfig.xml");
        try {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigurationException("Configuration file appConfig.xml couldn't be read",e);
        } finally {
            if(inputStream!=null)
                try {inputStream.close();} catch (IOException e) {e.printStackTrace();}
        }

        String applicationName = properties.getProperty("APPLICATION_NAME");
        String[] ssoProviders = properties.getProperty("SSO_PROVIDERS").split(",");
        String factoryClass = properties.getProperty("FACTORY");
        String overview = properties.getProperty("OVERVIEW");

        initializeSendMail(properties);

        initializeCommandExecution(properties);

        application.setAttribute("executor",executor);
        application.setAttribute("name",applicationName);
        application.setAttribute("providers",ssoProviders);
        application.setAttribute("overview",overview);

        instantiateFactory(servletContextEvent, factoryClass);
    }

    private void initializeCommandExecution(Properties properties) {
        int maxRunningCommands = Integer.valueOf(properties.getProperty("MAX_COMMANDS"));
        String userCommands = properties.getProperty("MAX_USER_COMMANDS");
        String executionStrategy = properties.getProperty("EXECUTION_STRATEGY");

        if(executionStrategy.equals("REFUSE"))  {
            int maxUserCommands;
            try {
                maxUserCommands = Integer.parseInt(userCommands);
            } catch (NumberFormatException e) {
                maxUserCommands = 3;
            }
            executor = new RefuseCommandExecutor(maxRunningCommands,maxUserCommands);
        } else
            executor = new QueuedCommandExecutor(maxRunningCommands);
    }

    private void initializeSendMail(Properties properties) {
        String smtpHost = properties.getProperty("SMTP_HOST");
        String smtpPort = properties.getProperty("SMTP_PORT");
        String username = properties.getProperty("MAIL_USERNAME");
        String password = properties.getProperty("MAIL_PASSWORD");

        if(StringUtils.isNotEmpty(smtpHost) && StringUtils.isNotEmpty(smtpPort) && StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password))
            SendMail.initMail(username,password,smtpHost,smtpPort);
    }

    private void instantiateFactory(ServletContextEvent servletContextEvent, String factoryClass) {
        try {
            CommandPageFactory commandPageFactory = (CommandPageFactory) getClass().getClassLoader().loadClass(factoryClass).newInstance();
            servletContextEvent.getServletContext().setAttribute("factory",commandPageFactory);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("executor",null);
        servletContextEvent.getServletContext().setAttribute("factory",null);
        executor.shutDown();
    }
}
