package com.pech.currcalcback;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@ApplicationPath("/")
public class AppConfig extends Application {

    private final String PROPERTIES_FILE = "dev/config.properties";
    private static Properties properties = new Properties();

    public AppConfig() {
        readProperties();
    }

    private Properties readProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static String getProperty(String name){
        return properties.getProperty(name);
    }

}
