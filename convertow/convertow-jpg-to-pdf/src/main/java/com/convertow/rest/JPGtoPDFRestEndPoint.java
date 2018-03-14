package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.functions.ConvertOwFunctions;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Miroslav on 14.3.2018.
 */
@Path("/jpgtopdf")
public class JPGtoPDFRestEndPoint <D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> {
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    public static ConvertOwFunctions coreFunctions = new ConvertOwFunctions();
    private static final Logger log = LoggerFactory.getLogger(JPGtoPDFRestEndPoint.class);

    @Inject
    public JPGtoPDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".jpg" ;
        String filePathPdf= PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        OutputStream file = new FileOutputStream(filePathPdf);

        coreFunctions.imageToPdf(filePath, file);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from JPG to PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
