package com.licenta.core;

import com.licenta.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Lucian Condescu, 2017.
 */
public enum  PropertiesManager {
    INSTANCE;
    private Properties properties;

    PropertiesManager() {
        properties = new Properties();
        final String confFile = "dev".equals(System.getProperty("ENV")) ? "dev/config/appConfig.xml" : "config/appConfig.xml";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(confFile);
        try {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigurationException("Configuration file appConfig.xml couldn't be read",e);
        } finally {
            if(inputStream!=null)
                try {inputStream.close();} catch (IOException e) {e.printStackTrace();}
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
