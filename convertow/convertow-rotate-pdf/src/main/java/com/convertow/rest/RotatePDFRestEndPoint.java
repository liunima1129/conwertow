package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.convertow.utils.RotatePDFUtil;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.kernel.pdf.*;

import java.io.File;
/**
 * Created by Miroslav on 06.3.2018.
 */
@Path("/rotatepdf")
public class RotatePDFRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface {
    private static final Logger log = LoggerFactory.getLogger(RotatePDFRestEndPoint.class);
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();
    @Inject
    public RotatePDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("degrees") String degrees) throws RepositoryException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        String filePathRotated = PATH + id + DELIMITER + nameWithoutExtension + "Rotated.pdf" ;
        File file = new File(filePathRotated);
        try {
            RotatePDFUtil.manipulatePdf(filePath, filePathRotated, degrees);
        }catch (Exception e){

        }


        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds to rotate PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
