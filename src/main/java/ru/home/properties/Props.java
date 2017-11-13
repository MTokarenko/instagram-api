package ru.home.properties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class Props {

    private static final Properties properties = new Properties();


    public static String getProperty(String key) {
        try {
            String target = System.getProperty("target", "local");
            properties.load(new FileReader(getResourceFile(String.format("%s.properties", target))));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static File getResourceFile(String fileName) {
        try {
            return new File(Props.class.getClassLoader().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
