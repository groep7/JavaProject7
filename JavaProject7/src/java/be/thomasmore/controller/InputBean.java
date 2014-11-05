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
            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = worksheet.iterator();

            List<Student> studenten = new ArrayList();
            Test test = new Test();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (!cell.getStringCellValue().equals("klas")) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    Klas klas = new Klas();
                                    klas.setNaam(cell.getStringCellValue());
                                    javaProject7Service.addKlas(klas);
                                    break;
                            }
                        }
                    }
                }

                if (row.getRowNum() == 1) { //test opstellen
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (!cell.getStringCellValue().equals("Vak")) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    break;
                            }
                        }
                    }
                }

                if (row.getRowNum() > 5) { // enkel studenten, niet de dingen erboven
                    //For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();

                    Student student = new Student();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                student.setStudentennummer((int) cell.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                if (cell.getStringCellValue().equals("zit al in de DB")) {
                                    break;
                                } else {
                                    String volledigeNaam = cell.getStringCellValue();
                                    String[] delen = volledigeNaam.split(" ");
                                    student.setVoornaam(delen[0]);
                                    student.setAchternaam(delen[1]);
                                    break;
                                }
                        }
                        studenten.add(student);
                    }
                }
            }

            for (Student student : studenten) {
                javaProject7Service.addStudent(student);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
