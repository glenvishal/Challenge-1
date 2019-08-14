package com.challenge;

import com.file.CreateCsvFileImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args )
    {
        CreateCsvFileImpl readFile = new CreateCsvFileImpl(loadPropertiesFile());
        List<String> fileModelList = readFile.readFromTextFile();
        readFile.createFile(fileModelList);
    }

    public static Properties loadPropertiesFile(){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();
        try{
            appProps.load(new FileInputStream(appConfigPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appProps;
    }
}
