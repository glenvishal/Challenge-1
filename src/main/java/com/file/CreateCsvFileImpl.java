package com.file;

import com.constants.FieldLength;
import com.constants.FileHeader;
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
        List<String> fileModelList = new ArrayList<String>();
        try {
            fileModelList.add(getHeaderNames());

            Stream<String> lines = Files.lines(Paths.get(inputFilePath));
            System.out.println("<!-----Read all lines as a Stream-----!>");
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
            });
            lines.close();


            fileModelList.forEach(f -> System.out.println(f.toString()));
        } catch(IOException io) {
            io.printStackTrace();
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
        //Get the file reference
        Path path = Paths.get(outputFilePath);
        try
        {

            Files.write(path,fileModelList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}