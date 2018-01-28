package com.convertow.rest;

import com.convertow.functions.ConvertOwFunctions;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miroslav on 24.1.2018.
 */
@Path("/svgtojpg")
public class SvgToJpgRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface {
    private static final Logger log = LoggerFactory.getLogger(SvgToJpgRestEndPoint.class);

    @Inject
    public SvgToJpgRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        /*String filePath = PATH + id + "\\" + name;*/
        String filePath = PATH + id + "\\" + nameWithoutExtension + ".svg" ;
        String filePathJpg = PATH + id + "\\" + nameWithoutExtension + ".jpg" ;

        functions.convertFromSVG(filePath, filePathJpg, "jpg");
        functions.deleteFilesInFolder(PATH + id, "jpg");

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from SVG to JPG");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
