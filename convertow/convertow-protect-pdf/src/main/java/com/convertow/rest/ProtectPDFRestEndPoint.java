package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Miroslav on 05.2.2018.
 */
@Path("/protectpdf")
public class ProtectPDFRestEndPoint <D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface{
    private static final Logger log = LoggerFactory.getLogger(ProtectPDFRestEndPoint.class);
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();
    @Inject
    public ProtectPDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("password") String password) throws RepositoryException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        String filePathProtected = PATH + id + DELIMITER + nameWithoutExtension + "Protected.pdf" ;
        try {
            encryptPdf(filePath, filePathProtected, password);
        } catch (IOException e) {
            log.error("I/O Exception ");
        } catch (DocumentException e) {
            log.error("Document Exception");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds to protect PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }

    public void encryptPdf(String src, String dest, String password) throws IOException, DocumentException{
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setEncryption(password.getBytes(), password.getBytes(),PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
        stamper.close();
        reader.close();
    }
}
