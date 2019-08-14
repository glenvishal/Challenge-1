package com.file;

import com.constants.FieldLength;
import com.constants.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateCsvFileImpl implements FileOperations{
    public static Logger log = LoggerFactory.getLogger(CreateCsvFileImpl.class);
    private Properties appProp = null;
    private String inputFilePath = null;
    private String outputFilePath = null;

    private static final String fieldSeperator = ",";
    private static final String newFieldLine = "\n";

    public CreateCsvFileImpl(Properties appProp) {
        this.appProp = appProp;
        this.inputFilePath = appProp.getProperty("file.path.input");
        this.outputFilePath = appProp.getProperty("file.path.output");
    }

    public List<String> readFromTextFile(){
        log.info("Reading file "+inputFilePath);
        List<String> fileModelList = new ArrayList<String>();
        try {
            fileModelList.add(getHeaderNames());
            log.debug("Header Names: "+fileModelList);

            Stream<String> lines = Files.lines(Paths.get(inputFilePath));
            lines.forEach(line ->{
                StringBuffer tempBuffer = new StringBuffer();
                tempBuffer.append(line.substring(FieldLength.CLIENT_INFORMATION_START.getValue(),
                        FieldLength.CLIENT_INFORMATION_END.getValue())).append(fieldSeperator);
                tempBuffer.append(line.substring(FieldLength.PRODUCT_INFORMATION_START.getValue(),
                        FieldLength.PRODUCT_INFORMATION_END.getValue())).append(fieldSeperator);

                String quantityLong = line.substring(FieldLength.QUANTITY_LONG_START.getValue(),
                        FieldLength.QUANTITY_LONG_END.getValue());
                String quantityShort = line.substring(FieldLength.QUANTITY_SHORT_START.getValue(),
                        FieldLength.QUANTITY_SHORT_END.getValue());
                tempBuffer.append(String.valueOf(Integer.parseInt(quantityLong)-Integer.parseInt(quantityShort))).append(newFieldLine);

                fileModelList.add(tempBuffer.toString());
                log.debug("Data from input file: "+fileModelList);
            });
            lines.close();
        } catch(IOException io) {
            log.error("Error reading file", io);
        }

        return fileModelList;
    }

    private String getHeaderNames(){
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append(FileHeader.CLIENT_INFORMATION.getHeaderName()).append(fieldSeperator);
        tempBuffer.append(FileHeader.PRODUCT_INFORMATION.getHeaderName()).append(fieldSeperator);
        tempBuffer.append(FileHeader.TOTAL_TRANSACTION_AMOUNT.getHeaderName()).append(newFieldLine);
        return tempBuffer.toString();
    }

    public void createFile(List<String> fileModelList){
        Path path = Paths.get(outputFilePath);
        try
        {
            Files.write(path,fileModelList);
            log.info("CSV file successfully created at the location: "+outputFilePath);
        } catch (IOException e) {
            log.error("Error creating file", e);
        }
    }
}