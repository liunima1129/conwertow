package com.convertow.models;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.ReaderProperties;
import java.io.File;
import java.io.IOException;

/**
 * Created by Miroslav on 06.2.2018.
 */
public class ITextOverideModel{

    public void manipulatePdf(String source, String dest) throws Exception {
        MyReader reader = new MyReader(source);
        reader.setUnethicalReading(true);
        reader.decryptOnPurpose();
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(dest));
        pdfDoc.close();
        reader.close();
    }

    class MyReader extends PdfReader {
        public MyReader(String filename) throws IOException {
            super(filename);
        }
        public void decryptOnPurpose() {
            encrypted = false;
        }
    }
}
