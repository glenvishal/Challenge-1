package com.file;

import java.util.List;

public interface FileOperations {
    List<String> readFromTextFile();
    void createFile(List<String> fileModelList);
}
