package com.file;

import com.constants.FieldLength;
import com.constants.FileHeader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CreateCsvFileImplTest {
    private static final String value = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
    FileOperations cvsFile;
    String inputPath;
    String outputPath;

    @Before
    public void init(){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "apptest.properties";

        Properties appProps = new Properties();
        try{
            appProps.load(new FileInputStream(appConfigPath));
            inputPath = appProps.getProperty("file.path.input");
            outputPath = appProps.getProperty("file.path.output");
            File file = new File(inputPath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            //BufferedWriter bw = Files.newBufferedWriter(path);
            bw.write(value);
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cvsFile = new CreateCsvFileImpl(appProps);
    }

    @After
    public void tearDown() {

        try {
            Files.delete(Paths.get(inputPath));
            Files.delete(Paths.get(outputPath));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void testFileHeaderNames()
    {
        cvsFile.createFile();
        try {
            Stream<String> lines = Files.lines(Paths.get(outputPath));
            String[] headers = lines.findFirst().get().trim().replaceAll("\n ", "").split(",");
            assertThat(headers[0], is(FileHeader.CLIENT_INFORMATION.getHeaderName()));
            assertThat(headers[1], is(FileHeader.PRODUCT_INFORMATION.getHeaderName()));
            assertThat(headers[2], is(FileHeader.TOTAL_TRANSACTION_AMOUNT.getHeaderName()));
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileValues()
    {
        cvsFile.createFile();
        try {
            Stream<String> lines = Files.lines(Paths.get(outputPath));

            String[] values = lines.toArray(String[]::new);
            String[] lineValues = values[1].trim().replaceAll("\n ", "").split(",");

            assertThat(lineValues[0], is(value.substring(FieldLength.CLIENT_INFORMATION_START.getValue(),
                    FieldLength.CLIENT_INFORMATION_END.getValue())));
            assertThat(lineValues[1], is(value.substring(FieldLength.PRODUCT_INFORMATION_START.getValue(),
                    FieldLength.PRODUCT_INFORMATION_END.getValue())));

            String quantityLong = value.substring(FieldLength.QUANTITY_LONG_START.getValue(),
                    FieldLength.QUANTITY_LONG_END.getValue());
            String quantityShort = value.substring(FieldLength.QUANTITY_SHORT_START.getValue(),
                    FieldLength.QUANTITY_SHORT_END.getValue());

            assertThat(Integer.parseInt(lineValues[2]), is(Integer.parseInt(quantityLong)-Integer.parseInt(quantityShort)));


            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
