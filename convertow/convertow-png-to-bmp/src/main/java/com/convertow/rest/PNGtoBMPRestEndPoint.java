package com.convertow.rest;

import com.convertow.ConvertOwCore;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Miroslav on 20.3.2018.
 */
@Path("/pngtobmp")
public class PNGtoBMPRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> {

    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    private static final Logger log = LoggerFactory.getLogger(PNGtoBMPRestEndPoint.class);

    @Inject
    public PNGtoBMPRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".png" ;
        String filePathPng = PATH + id + DELIMITER + nameWithoutExtension + ".jpg" ;
        String filePathBmp = PATH + id + DELIMITER + nameWithoutExtension + ".bmp" ;

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            outputStream = new FileOutputStream(filePathBmp);
            // reads input image from file
            BufferedImage inputImage = ImageIO.read(inputStream);
            // writes to the output image in specified format
            boolean result = ImageIO.write(inputImage, "bmp", outputStream);
            // needs to close the streams
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            log.error("Can't convert PNG to JPG " + e);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from PNG to BMP");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}

