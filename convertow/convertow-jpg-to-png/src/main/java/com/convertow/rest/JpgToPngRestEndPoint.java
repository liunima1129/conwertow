package com.convertow.rest;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;

import javax.imageio.ImageIO;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFToImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Miroslav on 22.1.2018.
 */
@Path("/jpgtopng")
public class JpgToPngRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> {

    private static final Logger log = LoggerFactory.getLogger(JpgToPngRestEndPoint.class);
    /*local test*/
    private static final String PATH = "D:\\docroot\\fileUpload\\";
    /*server*/

    @Inject
    public JpgToPngRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        /*String filePath = PATH + id + "\\" + name;*/
        String filePath = PATH + id + "\\01-Voved_Intro_to_QoS17.jpg" ;
        String filePathPng = PATH + id + "\\01-Voved_Intro_to_QoS17.png" ;

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            outputStream = new FileOutputStream(filePathPng);
            // reads input image from file
            BufferedImage inputImage = ImageIO.read(inputStream);
            // writes to the output image in specified format
            boolean result = ImageIO.write(inputImage, "png", outputStream);
            // needs to close the streams
            outputStream.close();
            inputStream.close();
        }catch (Exception e){

        }

        File folder = new File(PATH + id );

        File []listFiles=folder.listFiles();
        for(int i=0;i<listFiles.length;i++)
        {
            if( !listFiles[i].getName().endsWith("png")) {
                listFiles[i].delete();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from JPG to PNG");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
