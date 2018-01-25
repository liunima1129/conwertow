package com.convertow.functions;

import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReaderSpi;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriterSpi;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.zip.ZipEntry;

/**
 * Created by Miroslav on 26.1.2018.
 */
public class ConvertOwFunctions {
    @Inject
    public ConvertOwFunctions(){}

    public boolean saveTiff(String filename, BufferedImage image) {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.registerServiceProvider(new TIFFImageWriterSpi());
        registry.registerServiceProvider(new TIFFImageReaderSpi());

        File tiffFile = new File(filename);
        ImageOutputStream ios = null;
        ImageWriter writer = null;

        try {

            Iterator it = ImageIO.getImageWritersByFormatName("tiff");
            if (it.hasNext()) {
                writer = (ImageWriter)it.next();
            } else {
                return false;
            }

            ios = ImageIO.createImageOutputStream(tiffFile);
            writer.setOutput(ios);

            IIOImage iioImage = new IIOImage(image, null, null);

            writer.write(null, iioImage, null);
            ios.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void convertFromSVG(String filePath, String imagePath, String format) throws IOException {
        java.nio.file.Path path = Paths.get(filePath);
        File t = new File(imagePath);
        byte[] data = Files.readAllBytes(path);
        InputStream inputStream = new ByteArrayInputStream(data);
        OutputStream outputStream = new FileOutputStream(imagePath);
        Document doc = getDocument(inputStream);

        TranscoderInput input = new TranscoderInput(doc);
        TranscoderOutput output = new TranscoderOutput(outputStream);

        try {
            Transcoder transcoder = getTranscoder(format, 0.7f);
            transcoder.transcode(input, output);
        } catch (TranscoderException e) {
            e.printStackTrace();
        }
    }


    private Transcoder getTranscoder(String transcoderType, float keyQuality) {
        Transcoder transcoder = null;
        if (transcoderType.equals("png")) {
            transcoder = getPNGTranscoder();
        } else {
            transcoder = getJPEGTranscoder(keyQuality);

        }
        return transcoder;
    }

    private Document getDocument(InputStream inputStream) throws IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument("http://www.w3.org/2000/svg", inputStream);
        return doc;
    }

    private JPEGTranscoder getJPEGTranscoder(float keyQuality) {
        JPEGTranscoder jpeg = new JPEGTranscoder();
        jpeg.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(keyQuality));
        return jpeg;
    }

    private PNGTranscoder getPNGTranscoder() {
        return new PNGTranscoder();
    }

    public void deleteFilesInFolder(String path, String exclude){
        File folder = new File(path);

        //delete files
        File []listFiles=folder.listFiles();
        for(int i=0;i<listFiles.length;i++)
        {
            if( !listFiles[i].getName().endsWith(exclude)) {
                listFiles[i].delete();
            }

        }
    }
}
