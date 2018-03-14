package com.convertow.utils;

import com.itextpdf.kernel.pdf.*;

import java.io.File;

public class RotatePDFUtil{

    public static void manipulatePdf(String src, String dest, String degrees) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        int n = pdfDoc.getNumberOfPages();
        int rotateDegrees = Integer.parseInt(degrees);
        PdfPage page;
        PdfNumber rotate;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p);
            rotate = page.getPdfObject().getAsNumber(PdfName.Rotate);
            if (rotate == null) {
                page.setRotation(rotateDegrees);
            }
            else {
                page.setRotation((rotate.intValue() + rotateDegrees) % 360);
            }
        }
        pdfDoc.close();
    }
}
