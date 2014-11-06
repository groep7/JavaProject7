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
import be.thomasmore.model.Klas;
import be.thomasmore.model.Student;
import be.thomasmore.model.Test;
import be.thomasmore.service.JavaProject7Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ManagedBean(name = "inputBean")
@ViewScoped
public class InputBean implements Serializable {

    private static final long serialVersionUID = 9040359120893077422L;

    private Part part;
    private String statusMessage;

    @EJB
    private JavaProject7Service javaProject7Service;

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

        leesExcel(basePath + fileName);

        return null;    // return to same page
    }

    /**
     * Path to the resulting PDF file.
     */
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

    private void leesExcel(String path) {
        try {
            
            //declaratie en blad uit excel selecteren enzo
            FileInputStream fileInputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet worksheet = workbook.getSheet("Blad1");

//            XSSFRow row1 = worksheet.getRow(0);
//            XSSFCell cellA1 = row1.getCell((short) 0);
//            String a1Val = cellA1.getStringCellValue();
//            XSSFCell cellB1 = row1.getCell((short) 1);
//            String b1Val = cellB1.getStringCellValue();
//
//            XSSFRow row2 = worksheet.getRow(1);
//            XSSFCell cellA2 = row2.getCell((short) 0);
//            String a2Val = cellA2.getStringCellValue();
//            XSSFCell cellB2 = row2.getCell((short) 1);
//            String b2Val = cellB2.getStringCellValue();
//
//            XSSFRow row7 = worksheet.getRow(6);
//            int a7Val = (int) row7.getCell((short) 0).getNumericCellValue();
//            String b7Val = row7.getCell((short) 1).getStringCellValue();
//            int c7Val = (int) row7.getCell((short) 2).getNumericCellValue();
//
//            XSSFRow row8 = worksheet.getRow(7);
//            int a8Val = (int) row8.getCell((short) 0).getNumericCellValue();
//            String b8Val = row8.getCell((short) 1).getStringCellValue();
//            int c8Val = (int) row8.getCell((short) 2).getNumericCellValue();
//
//            XSSFRow row9 = worksheet.getRow(8);
//            int a9Val = (int) row9.getCell((short) 0).getNumericCellValue();
//            String b9Val = row9.getCell((short) 1).getStringCellValue();
//            int c9Val = (int) row9.getCell((short) 2).getNumericCellValue();
//            System.out.println("A1: " + a1Val);
//            System.out.println("B1: " + b1Val);
//            System.out.println("A2: " + a2Val);
//            System.out.println("B2: " + b2Val);
//            System.out.println("Studentnr - naam - score");
//            System.out.println(a7Val + " " + b7Val + " " + c7Val);
//            System.out.println(a8Val + " " + b8Val + " " + c8Val);
//            System.out.println(a9Val + " " + b9Val + " " + c9Val);
            
            //iterator dat door alle rijen gaat van het excel-blad
            Iterator<Row> rowIterator = worksheet.iterator();

            List<Student> studenten = new ArrayList(); //lijst aanmaken met alle studenten uit excel
            Test test = new Test(); //test aanmaken

            while (rowIterator.hasNext()) { //als er nog een rij bestaat die niet leeg is
                Row row = rowIterator.next(); //row-object aanmaken van huidige rij

                if (row.getRowNum() == 0) { //als de nummer van de rij = 0 (dus de 0de rij van het excel bestand = klas)
                    Iterator<Cell> cellIterator = row.cellIterator(); //voor deze rij elke cel in deze rij afgaan met een iterator

                    while (cellIterator.hasNext()) { //als er nog een cell bestaat die niet leeg is
                        Cell cell = cellIterator.next(); //cell-object aanmaken van huidige cell

                        if (!cell.getStringCellValue().equals("klas")) { //als er het woord "klas" in de cell staat, deze overslaan. Als de cel van de 0de rij (klas-rij) iets anders is dan "klas" dus (=A1 in excel)
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING: //als het type van de cel een string is
                                    Klas klas = new Klas(); // klas-object aanmaken
                                    klas.setNaam(cell.getStringCellValue()); //naam van klas instellen op de waarde van de cell
                                    javaProject7Service.addKlas(klas); //verbinding met database (werkt niet denk ik)
                                    break;
                            }
                        }
                    }
                }

                //volgende if is hetzelfde principe als vorige enkel voor een andere rij
                if (row.getRowNum() == 1) { //nummer van de rij = 1 (dus eigenlijk in excel de 2de rij)
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (!cell.getStringCellValue().equals("Vak")) { //als er het woord "Vak" in de cell staat, deze overslaan
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    //hier moet nog code komen om het vak toe te voegen aan het Test-object (zie regel 196)
                                    break;
                            }
                        }
                    }
                }

                //weer hetzelfde principe als hierboven
                if (row.getRowNum() > 5) { // enkel voor de rijen 5 en hoger (dus enkel studenten)
                    Iterator<Cell> cellIterator = row.cellIterator();

                    Student student = new Student(); //nieuw student-object aanmaken per rij

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC: //als de cell een int is
                                student.setStudentennummer((int) cell.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING: //als de cell een string is
                                if (cell.getStringCellValue().equals("zit al in de DB")) { //als de cell "zit al in de DB" bevat, niets doen (zie excel; laatste regel)
                                    break;
                                } else {
                                    String volledigeNaam = cell.getStringCellValue();
                                    String[] delen = volledigeNaam.split(" ");
                                    student.setVoornaam(delen[0]);
                                    student.setAchternaam(delen[1]);
                                    break;
                                }
                        }
                        studenten.add(student); //student toevoegen aan studenten list
                    }
                }
            }

            // elke student uit de lits toevoegen aan de database
            for (Student student : studenten) {
                javaProject7Service.addStudent(student); // (geen idee of dit werkt)
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
