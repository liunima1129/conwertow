package com.convertow.rest;

import com.convertow.functions.ConvertOwFunctions;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.itextpdf.text.BadElementException;
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
import java.io.IOException;

/**
 * Created by Miroslav on 26.1.2018.
 */
@Path("/tifftojpg")
public class TiffToJpgRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface {
    private static final Logger log = LoggerFactory.getLogger(TiffToJpgRestEndPoint.class);

    @Inject
    public TiffToJpgRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        /*String filePath = PATH + id + "\\" + name;*/
        String filePath = PATH + id + "\\" + nameWithoutExtension + ".tiff" ;

        try {
            functions.convertFromTiffToImage(filePath, PATH + id + "\\" + nameWithoutExtension , ".jpg");
        } catch (BadElementException e) {
            log.error("Can't convert TIFF to Image");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from TIFF to JPG");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
