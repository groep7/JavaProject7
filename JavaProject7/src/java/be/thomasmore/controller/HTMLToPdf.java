/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.thomasmore.controller;

import java.io.FileOutputStream;
import java.io.StringReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author FonsDeSpons
 */

@ManagedBean(name = "HTMLToPDF")
@ViewScoped
public class HTMLToPdf {
    public String Write() throws FileNotFoundException {
        
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("DownloadedPDF.pdf"));
            
            document.open();
            Paragraph paragraph = new Paragraph();
            paragraph.add("This is super awesome. OMG FONS PLS");
            document.add(paragraph);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(HTMLToPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Wsup";
    }
}
