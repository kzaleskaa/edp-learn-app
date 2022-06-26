package com.finalproject.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static final String PROPERTIES_FILE_NAME = "/app.properties";
    private Properties properties = null;
    private static PropertiesManager instance;

    public PropertiesManager () {
        loadProperties(PROPERTIES_FILE_NAME);
    }

    public static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    public void loadProperties(String fileName) {
        properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new RuntimeException("Properties file '" + fileName + "' not found in classpath");
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
