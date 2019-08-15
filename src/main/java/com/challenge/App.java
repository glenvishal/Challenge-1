package com.challenge;

import com.file.CreateCsvFileImpl;
import com.file.FileOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class App {
    public static Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        log.info("Starting the App");
        FileOperations readFile = new CreateCsvFileImpl(loadPropertiesFile());
        readFile.createFile();
    }

    public static Properties loadPropertiesFile(){
        log.info("Configuration file loading");
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();
        try{
            appProps.load(new FileInputStream(appConfigPath));
            log.info("Configuration file loaded successfully");
        } catch (FileNotFoundException e) {
            log.error("Configuration file not found at the location", e);
        } catch (IOException e) {
            log.error("Error reading configuration file", e);
        }
        return appProps;
    }
}
