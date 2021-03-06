package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.itextpdf.text.DocumentException;
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
import java.io.File;
import java.io.IOException;

/**
 * Created by Miroslav on 20.3.2018.
 */
@Path("/bmptopng")
public class BMPtoPNGRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> {
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();
    private static final Logger log = LoggerFactory.getLogger(BMPtoPNGRestEndPoint.class);

    @Inject
    public BMPtoPNGRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException, DocumentException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".bmp" ;
        String filePathJpg = PATH + id + DELIMITER + nameWithoutExtension + ".png" ;

        BufferedImage bufImg = ImageIO.read(new java.io.File(filePath));
        File outputfile = new File(filePathJpg);
        ImageIO.write(bufImg, "png", outputfile);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from BMP to PNG");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}

