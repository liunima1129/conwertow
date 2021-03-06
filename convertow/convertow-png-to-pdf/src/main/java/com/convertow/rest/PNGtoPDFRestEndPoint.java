package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.functions.ConvertOwFunctions;
import com.itextpdf.text.*;
import info.magnolia.objectfactory.Components;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;

import javax.imageio.ImageIO;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

//The image class which will hold the PNG image as an object
import com.itextpdf.text.pdf.PdfWriter;
//This class is required to read PNG image into Image object
import com.itextpdf.text.pdf.codec.PngImage;
/**
 * Created by Miroslav on 14.3.2018.
 */
@Path("/pngtopdf")
public class PNGtoPDFRestEndPoint <D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D>{
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    public static ConvertOwFunctions coreFunctions = new ConvertOwFunctions();
    private static final Logger log = LoggerFactory.getLogger(PNGtoPDFRestEndPoint.class);

    @Inject
    public PNGtoPDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".png" ;
        String filePathPdf= PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        OutputStream file = new FileOutputStream(filePathPdf);

        coreFunctions.imageToPdf(filePath, file);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from PNG to PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
