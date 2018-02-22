package com.convertow.rest.multithreading;

import com.convertow.ConvertOwCore;
import com.convertow.rest.PdfToJpgRestEndPoint;
import info.magnolia.objectfactory.Components;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFToImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Miroslav on 17.1.2018.
 */
public class PdfToJpgMultithreading implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(PdfToJpgRestEndPoint.class);

    private static final String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    private String id;
    private String name;

    public PdfToJpgMultithreading(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        String filePath = PATH + id + DELIMITER + name;
        File file = new File(filePath);

        PDDocument document = null;
        try {
            document = PDDocument.load(new File(file.getAbsolutePath()));
        }catch (Exception e){
            log.error("Can't make document from file -> " + name);
        }
        // render the pages
        String numPages = String.valueOf(document.getPages().getCount());
        String filePrefix = PATH + DELIMITER + id + DELIMITER + nameWithoutExtension;

        String [] args_2 =  new String[9];
        String pdfPath = file.getAbsolutePath();
        args_2[0] = "-startPage";
        args_2[1] = "1";
        args_2[2] = "-endPage";
        args_2[3] = numPages;
        args_2[4] = "-outputPrefix";
        args_2[5] = filePrefix;
        //args_2[6] = "-resolution";
        //args_2[7] = "1000";
        args_2[6] = pdfPath;
        args_2[7] = "-format";
        args_2[8] = "jpg";

        try {
            PDFToImage.main(args_2);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        try {
            document.close();
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from PDF to JPG");
    }
}
