package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.convertow.models.ITextOverideModel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.apache.commons.lang3.StringUtils;
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
 * Created by Miroslav on 06.2.2018.
 */
@Path("/unlockpdf")
public class UnlockPDFRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface{
    private static final Logger log = LoggerFactory.getLogger(UnlockPDFRestEndPoint.class);
    private static final ITextOverideModel model = new ITextOverideModel();
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();
    @Inject
    public UnlockPDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("password") String password) throws RepositoryException, IOException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        String filePathDecrypt = PATH + id + DELIMITER + nameWithoutExtension + "Decrypt.pdf" ;

        if ( password == null || !StringUtils.isNoneBlank(password) || !StringUtils.isNoneEmpty(password)){
            try {
                model.manipulatePdf(filePath, filePathDecrypt);
            } catch (Exception e) {
                log.error("Can't Decrypt PDF file");
                String error = "{\"error\":1}";
                return Response.ok(error).build();
            }
        }else{
            try {
                decrypt(filePath, filePathDecrypt, password);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (com.lowagie.text.DocumentException e) {
                log.error("Can't Unlock PDF file");
            }
        }


        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for unlocking PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();

    }

    public void decrypt(String src, String dest, String password) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        PdfReader reader = new PdfReader(src, password.getBytes());
        System.out.println(new String(reader.computeUserPassword()));
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
}
