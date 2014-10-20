/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.thomasmore.controller;

/**
 *
 * @author Logic
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

@ManagedBean(name = "inputBean")
@ViewScoped
public class InputBean implements Serializable {

    private static final long serialVersionUID = 9040359120893077422L;

    private Part part;
    private String statusMessage;

    public String uploadFile() throws IOException {

        // Extract file name from content-disposition header of file part
        String fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);

        String basePath = "C:" + File.separator + "data" + File.separator;
        File outputFilePath = new File(basePath + fileName);

        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = part.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            statusMessage = "File upload successfull !!";
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage = "File upload failed !!";
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;    // return to same page
    }
    
    /** Path to the resulting PDF file. */
    public static final String RESULT
        = "D://hello.pdf";

    public static void Write() throws DocumentException, IOException {
        
        new InputBean().createPdf(RESULT);
    }
    public void createPdf(String filename)
	throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
}
