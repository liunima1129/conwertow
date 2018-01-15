package com.convertow.servlets;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Miroslav on 14.1.2018.
 */
public class FileUploaderRequestParser {
    private static String FILENAME_PARAM = "qqfile";

    private String filename;
    private FileItem uploadItem;
    private boolean generateError;

    private FileUploaderRequestParser() {
    }

    // 2nd param is null unless a MPFR
    static FileUploaderRequestParser getInstance(HttpServletRequest request, FileUploaderMultipartParser multipartUploadParser) throws Exception {
        FileUploaderRequestParser requestParser = new FileUploaderRequestParser();

        if (multipartUploadParser != null) {
            requestParser.uploadItem = multipartUploadParser.getFirstFile();
            requestParser.filename = multipartUploadParser.getFirstFile().getName();
        } else {
            requestParser.filename = request.getParameter(FILENAME_PARAM);
        }

        requestParser.generateError = Boolean.parseBoolean(request.getParameter("generateError"));

        return requestParser;
    }

    public String getFilename() {
        return filename;
    }

    // only non-null for MPFRs
    public FileItem getUploadItem() {
        return uploadItem;
    }

    public boolean generateError() {
        return generateError;
    }
}
