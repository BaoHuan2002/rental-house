package com.rentalhouse.app.views.components.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

public class PDFgenerator {
   // todo CREATE FILE CHOOSER WINDOW TO GET PATH WHERE FILE SAVING:
   public static void showWindowToSave(AnchorPane invoice_formPDF) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
      fileChooser.setTitle("Save invoice");
      
      // SHOW WINDOW CHOOSER TO GET PATH:
      File filePath = fileChooser.showSaveDialog(invoice_formPDF.getScene().getWindow());
      createPDFFile(filePath, invoice_formPDF);
   }

   // todo CREATE EMPTY PDF EMPTY:
   private static void createPDFFile(File filePath, AnchorPane scene) {
      if (filePath != null) {
         try {
            scene.getScene().getWindow().hide();
            File file = new File(filePath.getAbsolutePath());
            if (file.createNewFile()) {
               System.out.println("File created: " + filePath.getAbsolutePath());

               PdfWriter writer = new PdfWriter(new FileOutputStream(file));
               PdfDocument pdf = new PdfDocument(writer);
               pdf.setDefaultPageSize(PageSize.A4);
               createDocument(pdf, scene);
               System.out.println("PDF created successfully.");
            } else {
               System.err.println("File already exists: " + filePath.getAbsolutePath());
            }
         } catch (IOException e) {
            System.err.println("Create File PDF failed: " + e.getMessage());
         } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
         }
      }
   }

   // todo CREATE CONTENT FOR FILE PDF BY CREATE DOCUMENT WITHIN PDF FILE:
   private static void createDocument(PdfDocument pdf, AnchorPane scene) throws IOException {
      Document document = new Document(pdf);
      WritableImage snapshot = scene.snapshot(null, null);
      snapshot.isSmooth();
      BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
      Image image = new Image(ImageDataFactory.create(bufferedImage, null));
      document.add(image);
      document.close();
   }
}
