package com.licenta.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Lucian CONDESCU
 */
public class ProviderFactory {

    public static Provider getProviderByName(String providerName, javax.servlet.ServletContext context) {
        providerName = providerName.trim().toLowerCase();
        Properties properties = new Properties();
        InputStream inputStream = ProviderFactory.class.getClassLoader().getResourceAsStream("config/" + providerName + ".xml");
        try {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String apiKey = properties.getProperty("API_KEY");
        String apiSecret = properties.getProperty("API_SECRET");
        String domain = properties.getProperty("DOMAIN");
        if(domain.endsWith("/")) domain = domain.substring(0,domain.length() - 1);
        String callback = domain + context.getContextPath() + "/oauth2Callback";
        Provider provider = null;

        switch (providerName) {
            case "google" :
                provider = new GoogleProvider(apiKey, apiSecret, callback);
                break;
            case "facebook" :
                provider = new FacebookProvider(apiKey,apiSecret,callback);
                break;
            case "yahoo" :
                provider = new YahooProvider(apiKey,apiSecret,callback);
                break;
        }

        return provider;
    }
}
