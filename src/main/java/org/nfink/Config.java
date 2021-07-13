package org.nfink;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class Config {
    public static String getProperty(String name) {
        try {
            Configuration config = new Configurations().properties(new File("config.properties"));
            return config.getString(name);
        }
        catch (ConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
