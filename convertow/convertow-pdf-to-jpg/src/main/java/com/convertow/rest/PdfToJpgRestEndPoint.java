package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.convertow.rest.multithreading.PdfToJpgMultithreading;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFToImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.AbstractMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Miroslav on 16.1.2018.
 */
@Path("/pdftojpg")
public class PdfToJpgRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D>{
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    private static final Logger log = LoggerFactory.getLogger(PdfToJpgRestEndPoint.class);
    @Inject
    public PdfToJpgRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException{

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
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

        File zipFile = new File(PATH + id + DELIMITER + nameWithoutExtension  +".zip");
        FileOutputStream zipFileOut = null;
        try {
            zipFileOut = new FileOutputStream(zipFile);
        }catch (Exception e){
            log.error(String.valueOf(e));
        }

        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(zipFileOut));

        byte []b = new byte[1024];
        File folder = new File(PATH + id );

        try {
            //move files to zip
            File []listFiles=folder.listFiles();
            for(int i=0;i<listFiles.length;i++)
            {
                if( !listFiles[i].getName().endsWith("zip")) {
                    b = new byte[(int) listFiles[i].length()];
                    FileInputStream fin = new FileInputStream(listFiles[i]);
                    zipOutputStream.putNextEntry(new ZipEntry(listFiles[i].getName()));
                    int length;
                    while ((length = fin.read(b, 0, 1024)) > 0) {
                        zipOutputStream.write(b, 0, length);
                    }
                    zipOutputStream.closeEntry();
                    fin.close();
                    listFiles[i].delete();
                }

            }
        }catch (Exception e){
            log.error(String.valueOf(e));
        }

        try {
            zipOutputStream.close();

        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from PDF to JPG");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }

}
