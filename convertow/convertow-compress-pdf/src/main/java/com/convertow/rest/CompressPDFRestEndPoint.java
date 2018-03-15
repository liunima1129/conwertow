package com.convertow.rest;

import com.convertow.ConvertOwCore;
import com.itextpdf.text.DocumentException;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;
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
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Miroslav Pashaliski on 3/15/2018.
 */
@Path("/compresspdf")
public class CompressPDFRestEndPoint <D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> {
    String PATH = Components.getComponent(ConvertOwCore.class).getPath();
    String DELIMITER = Components.getComponent(ConvertOwCore.class).getFileDelimiter();

    private static final Logger log = LoggerFactory.getLogger(CompressPDFRestEndPoint.class);

    @Inject
    public CompressPDFRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException, DocumentException,Exception  {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));

        String filePath = PATH + id + DELIMITER + nameWithoutExtension + ".pdf" ;
        String filePathPdf= PATH + id + DELIMITER + nameWithoutExtension + "Compressed.pdf" ;


        PdfReader reader = new PdfReader(filePath);
        Document document = new Document(reader.getPageSizeWithRotation(1));
        PdfSmartCopy writer = new PdfSmartCopy(document, new FileOutputStream(filePathPdf));
        document.open();

        int total = reader.getNumberOfPages() + 1;
        for ( int i=1; i<total; i++) {
            PdfImportedPage page = writer.getImportedPage(reader, i);
            writer.setFullCompression();
            writer.addPage(page);
        }
        document.close();
        writer.close();

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for compress PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
