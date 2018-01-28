package com.convertow.rest;

import com.convertow.interfaces.ConvertowFunctionsInterface;
import com.itextpdf.text.*;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReaderSpi;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriterSpi;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.stream.ImageInputStream;

import com.itextpdf.text.pdf.PdfWriter;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFDecodeParam;

/**
 * Created by Miroslav on 26.1.2018.
 */
@Path("/tifftopdf")
public class TiffToPdfRestEndPoint<D extends ConfiguredEndpointDefinition> extends AbstractEndpoint<D> implements ConvertowFunctionsInterface {
    private static final Logger log = LoggerFactory.getLogger(TiffToPdfRestEndPoint.class);

    @Inject
    public TiffToPdfRestEndPoint(final D endpointDefinition) {
        super(endpointDefinition);
    }

    @GET
    @Path("/convert")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public Response getNewsByNodeName(@QueryParam("id") String id, @QueryParam("name") String name) throws RepositoryException, IOException, DocumentException {

        long startTime = System.currentTimeMillis();

        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        /*String filePath = PATH + id + "\\" + name;*/
        String filePath = PATH + id + "\\" + nameWithoutExtension + ".tiff" ;
        String filePathPdf = PATH + id + "\\" + nameWithoutExtension + ".pdf" ;

        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(filePathPdf);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        writer.open();
        document.open();

        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.registerServiceProvider(new TIFFImageWriterSpi());
        registry.registerServiceProvider(new TIFFImageReaderSpi());

        Iterator readers = javax.imageio.ImageIO.getImageReadersBySuffix("tiff");
        if (readers.hasNext()) {
            File fi = new File(filePath);
            ImageInputStream iis = javax.imageio.ImageIO
                    .createImageInputStream(fi);
            TIFFDecodeParam param = null;
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", fi, param);
            int pageCount = dec.getNumPages();
            ImageReader _imageReader = (ImageReader) (readers.next());
            if (_imageReader != null) {
                _imageReader.setInput(iis, true);
                int count = 1;
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage bufferedImage = _imageReader.read(i);
                    BufferedImage img2 = new BufferedImage(
                            bufferedImage.getWidth(),
                            bufferedImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    // Set the RGB values for converted image (jpg)
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        for (int x = 0; x < bufferedImage.getWidth(); x++) {
                            img2.setRGB(x, y, bufferedImage.getRGB(x, y));
                        }
                    }
                    System.out.println("Page: " + (i + 1));
                    // Set the RGB values for converted image (jpg)
                    //To image
                    /*String s = PATH + id + "\\" + nameWithoutExtension +i+ ".jpg";
                    ImageIO.write(img2, "jpg", new File(s));*/
                    //To pdf
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(img2, "jpg", baos);
                    baos.flush();
                    // Convert byteArrayoutputSteam to ByteArray
                    byte[] imageInByte = baos.toByteArray();
                    document.add(Image.getInstance(imageInByte));
                    baos.close();
                }
                document.close();
            }
        }


        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds for converting from TIFF to PDF");

        String success = "{\"success\":1}";
        return Response.ok(success).build();
    }
}
