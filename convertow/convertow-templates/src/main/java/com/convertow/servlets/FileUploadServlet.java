package com.convertow.servlets;

import com.vaadin.server.UploadException;
import info.magnolia.cms.beans.runtime.Document;
import info.magnolia.cms.beans.runtime.MultipartForm;
import info.magnolia.cms.filters.MultipartRequestWrapper;
import info.magnolia.context.MgnlContext;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Miroslav on 14.1.2018.
 */
public class FileUploadServlet extends HttpServlet {
    private static final String SERVLET_INIT_PARAM_TMP_FOLDER = "tmpFolder";

    private static final long serialVersionUID = 1L;

    private static final String CONTEXT_PATH_PLACEHOLDER = "${contextPath}";

    /*private static final String DEFAULT_UPLOAD_DIR = "/docroot/fileUpload";*/
    private static final String DEFAULT_UPLOAD_DIR = "D:\\docroot\\fileUpload";

    private static final String SERVLET_INIT_PARAM_FORM_FIELD_NAME = "formFieldName";

    private static final String DEFAULT_FORM_FIELD_NAME = "files";

    final FastDateFormat fdf = FastDateFormat.getInstance("DDD, dd MMM yyyy HH:mm:ss");

    /*private static final String DOWNLOAD_PATH = "/opt/tomcat/webapps/ROOT/docroot/";*/
    private static final String DOWNLOAD_PATH = "D:\\docroot\\fileUpload";

    private static final Logger log = LoggerFactory.getLogger(FileUploadServlet.class);
    private Boolean errors = false;
    private static String CONTENT_LENGTH = "Content-Length";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        JSONObject jsonResponse = new JSONObject();
        List<String> uploadedFiles = new ArrayList<>();
        String path = null;
        String contentLengthHeader = request.getHeader(CONTENT_LENGTH);
        Long expectedFileSize = StringUtils.isBlank(contentLengthHeader) ? null : Long.parseLong(contentLengthHeader);
        setErrors(false);

        final String uploadDirParam = getUploadDirParam();
        final String formFieldNameParam = getFormFieldNameParam();

        File uploadDir = null;
        Map<String, Object> parameters = new LinkedHashMap<String, Object>();

        String originFileName = "";

        try {
            String lastModified = fdf.format(Calendar.getInstance().getTime());

            response.setContentType("application/json");
            response.setHeader("Content-Disposition", "inline");

            response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
            response.setHeader("Last-Modified", lastModified);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");

            response.setStatus(200);

            // random name to folder to keep file name
            final String uuid = java.util.UUID.randomUUID().toString();
            uploadDir = checkConfiguration(uploadDirParam + System.getProperty("file.separator") + uuid.toString());

            if (uploadDir != null) {
                File file = null;

                if (ServletFileUpload.isMultipartContent(request)) {
                    file = upload(request, response, uploadDir, formFieldNameParam);
                } else {

                    FileUploaderRequestParser requestParser = FileUploaderRequestParser.getInstance(request, null);

                    final File fileDir = new File(uploadDir.getAbsolutePath());

                    if (fileDir.mkdir()) {
                        fileDir.setReadable(true, false);
                        fileDir.setWritable(true, false);
                        fileDir.setExecutable(true, false);
                    }

                    originFileName = requestParser.getFilename();

                    file = new File(uploadDir.getAbsolutePath() + System.getProperty("file.separator") + originFileName);

                    writeToTempFile(request.getInputStream(), file, expectedFileSize);

                    writeResponse(response.getWriter(), requestParser.generateError() ? "Generated error" : null, file.getAbsolutePath());
                }
                jsonResponse.put("uuid", uuid);
            }
        }catch (Exception e){
            jsonResponse.put("uuid", null);
            response.getWriter().write(jsonResponse.toString());
        }
        long endTime = System.currentTimeMillis();

        System.out.println("That took " + (endTime - startTime) + " milliseconds for file upload");

        response.getWriter().write(jsonResponse.toString());
    }

    private String getUploadDirParam() {
        String param = StringUtils.defaultIfEmpty(getInitParameter(SERVLET_INIT_PARAM_TMP_FOLDER), DEFAULT_UPLOAD_DIR);
        if (StringUtils.contains(param, CONTEXT_PATH_PLACEHOLDER)) {
            final String contextPath = MgnlContext.getContextPath();
            param = StringUtils.replace(param, CONTEXT_PATH_PLACEHOLDER, contextPath);
        }
        return param;
    }

    private String getFormFieldNameParam() {
        String param = StringUtils.defaultIfEmpty(getInitParameter(SERVLET_INIT_PARAM_FORM_FIELD_NAME), DEFAULT_FORM_FIELD_NAME);
        return param;
    }

    protected void writeResponse(PrintWriter writer, String failureReason, String fileName) {

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        if (failureReason == null) {
            sb.append("\"success\": true");
        } else {
            sb.append("\"error\": \"").append(failureReason).append("\"");
        }

        if (null != fileName) {
            sb.append(", \"fileName\": \"").append(fileName.replace("\\", "\\\\")).append("\"");
        }
        sb.append("}");
        writer.print(sb.toString());
    }

    private File checkConfiguration(String uploadDirParam) throws FileUploadException {


        final String contextPath = MgnlContext.getContextPath();

        File uploadDir = null;
        String uploadDirPath = uploadDirParam;

        try {
            uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new UploadException("Upload dir [" + uploadDirPath + "] does not exist and I was unable to create it automatically");
                }
            }
            if (!uploadDir.canWrite()) {
                throw new UploadException("Upload dir [" + uploadDirPath + "] not writable");
            }
        } catch (Exception e) {

        }

        return uploadDir;
    }

    protected File upload(final HttpServletRequest request, final HttpServletResponse response, final File uploadDir, final String formFieldName) throws ServletException, IOException {

        try {

            final MultipartForm form = (MultipartForm) request.getAttribute(MultipartForm.REQUEST_ATTRIBUTE_NAME);

            if (null != form) {
                Document uploadedFile = form.getDocument(formFieldName);

                if( uploadedFile == null ){
                    File file = new File("C:\\Users\\Miroslav\\Downloads\\icon-github.tiff");
                    uploadedFile = new Document(file, "");
                }
                if (null != uploadedFile) {

                    // String fileName = MagnoliaValidationUtils.getSafeMgnlName(uploadedFile.getFile().getName());
                    String fileName = uploadedFile.getFile().getName();

                    // Verify same name on filesystem
                    File file = new File(uploadDir.getAbsolutePath(), fileName);

                    writeToTempFile(uploadedFile.getStream(), file, null);

                    log.info("File has been written to disk: {}", file.getAbsolutePath());

                    return file;

                } else {
                    writeResponse(response.getWriter(), "No file found in Multipart form", null);
                }
            } else {
                writeResponse(response.getWriter(), "Not a Multipart form", null);
            }

        } catch (Exception e) {
            writeResponse(response.getWriter(), "Upload exception: " + e.getMessage(), null);
        } catch (Throwable k) {
            writeResponse(response.getWriter(), "Unknown exception: " + k.getMessage(), null);
        }

        return null;

    }

    protected File writeToTempFile(InputStream in, File out, Long expectedFileSize) throws IOException {

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(out);

            IOUtils.copy(in, fos);

            if (expectedFileSize != null) {
                Long bytesWrittenToDisk = out.length();
                if (!expectedFileSize.equals(bytesWrittenToDisk)) {
                    log.warn("Expected file {} to be {} bytes; file on disk is {} bytes", new Object[] { out.getAbsolutePath(), expectedFileSize, 1 });
                    throw new IOException(String.format("Unexpected file size mismatch. Actual bytes %s. Expected bytes %s.", bytesWrittenToDisk, expectedFileSize));
                }
            }
            out.setReadable(true, false);
            return out;
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    public Boolean getErrors() {
        return errors;
    }

    public void setErrors(Boolean errors) {
        this.errors = errors;
    }
}
