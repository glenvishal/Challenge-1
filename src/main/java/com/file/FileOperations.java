package com.file;

import com.constants.FieldLength;
import com.model.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface FileOperations {
    Logger log = LoggerFactory.getLogger(FileOperations.class);
    void createFile();

    default List<FileModel> readFromTextFile(String filePathAndName){
        log.info("Reading file "+filePathAndName);
        List<FileModel> fileModelList = new ArrayList<FileModel>();
        try {
            Stream<String> lines = Files.lines(Paths.get(filePathAndName));
            lines.forEach(line ->{
                FileModel fileModel = new FileModel();
                fileModel.setClientInformation(line.substring(FieldLength.CLIENT_INFORMATION_START.getValue(),
                        FieldLength.CLIENT_INFORMATION_END.getValue()));
                fileModel.setProductInformation(line.substring(FieldLength.PRODUCT_INFORMATION_START.getValue(),
                        FieldLength.PRODUCT_INFORMATION_END.getValue()));
                fileModel.setQuantityLong(line.substring(FieldLength.QUANTITY_LONG_START.getValue(),
                        FieldLength.QUANTITY_LONG_END.getValue()));
                fileModel.setQuantityShort(line.substring(FieldLength.QUANTITY_SHORT_START.getValue(),
                        FieldLength.QUANTITY_SHORT_END.getValue()));
                fileModelList.add(fileModel);
                //log.debug("Data from input file: "+fileModelList);
            });
            lines.close();
        } catch(IOException io) {
            log.error("Error reading file", io);
        }
        return fileModelList;
    }
}
