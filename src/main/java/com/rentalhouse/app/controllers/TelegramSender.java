package com.rentalhouse.app.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.invoice.InvoicePDF;
import com.rentalhouse.app.views.components.invoice.LoadingController;
import com.rentalhouse.configs.DotEnv;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelegramSender {
   private static final String BOT_TOKEN = DotEnv.get("BOT_TOKEN");
   private static final String CHAT_ID_A100 = DotEnv.get("CHAT_ID_A100");

   // todo CREATE TELEGRAM WITH IMAGE:
   public static void sendTelegramPhoto(String chatId, String photoPath, String caption) {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
         HttpPost post = new HttpPost("https://api.telegram.org/bot" + BOT_TOKEN + "/sendPhoto");
         MultipartEntityBuilder builder = MultipartEntityBuilder.create();
         builder.addTextBody("chat_id", chatId, ContentType.TEXT_PLAIN);
         builder.addTextBody("caption", caption, ContentType.TEXT_PLAIN);

         if (photoPath != null && !photoPath.isEmpty()) {
            File photo = new File(photoPath);
            if (photo.exists()) {
               builder.addBinaryBody("photo", photo, ContentType.APPLICATION_OCTET_STREAM, photo.getName());
            } else {
               System.out.println("File not found: " + photoPath);
            }
         } else {
            System.out.println("Empty or null photoPath provided.");
         }

         HttpEntity entity = builder.build();
         post.setEntity(entity);

         HttpResponse response = httpClient.execute(post);
         HttpEntity responseEntity = response.getEntity();
         String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
         System.out.println("Telegram API Response: " + responseString);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // todo CREATE TELEGRAM WITH DOCUMENT ATTACHMENT FILE PDF:
   public static void sendTelegramDocument(String chatId, String documentPath, String caption) {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
         HttpPost post = new HttpPost("https://api.telegram.org/bot" + BOT_TOKEN + "/sendDocument");

         MultipartEntityBuilder builder = MultipartEntityBuilder.create();
         builder.addTextBody("chat_id", chatId, ContentType.TEXT_PLAIN);
         builder.addTextBody("caption", caption, ContentType.TEXT_PLAIN);

         if (documentPath != null && !documentPath.isEmpty()) {
            File documentFile = new File(documentPath);
            if (documentFile.exists()) {
               builder.addBinaryBody(
                     "document",
                     documentFile,
                     ContentType.APPLICATION_OCTET_STREAM,
                     documentFile.getName());
            } else {
               System.out.println("Document file not found: " + documentPath);
            }
         }

         HttpEntity entity = builder.build();
         post.setEntity(entity);

         HttpResponse response = httpClient.execute(post);
         HttpEntity responseEntity = response.getEntity();
         String responseString = EntityUtils.toString(responseEntity,
               StandardCharsets.UTF_8);
         System.out.println("Telegram API Response: " + responseString);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // todo CREATE METHOD RETURN BYTE[] TO GET DIRECT FILE PDF ON STAGE PDF:
   /**
    * @return use byte[] to create file PDF.
    */
   // public static byte[] createInvoicePDF(Invoice invoice) throws Exception {
   // FXMLLoader fxmlLoader = new
   // FXMLLoader(Objects.requireNonNull(InvoicePDF.class.getResource("printInvoice.fxml")));
   // StackPane stackPane = fxmlLoader.load();
   // InvoicePDF invoicePDF = fxmlLoader.getController();
   // invoicePDF.setData(invoice);

   // AnchorPane originalPdfPane = null;
   // for (Node node : stackPane.getChildren()) {
   // if (node instanceof AnchorPane) {
   // originalPdfPane = (AnchorPane) node;
   // break;
   // }
   // }

   // if (originalPdfPane == null) {
   // throw new RuntimeException("AnchorPane within stackPane not found!");
   // }

   // AnchorPane pdfPane = new AnchorPane();
   // pdfPane.getChildren().addAll(originalPdfPane.getChildren());

   // Scene tempScene = new Scene(pdfPane);
   // tempScene.getRoot().applyCss();
   // tempScene.getRoot().layout();

   // ByteArrayOutputStream baos = new ByteArrayOutputStream();
   // try {
   // PdfWriter writer = new PdfWriter(baos);
   // PdfDocument pdf = new PdfDocument(writer);
   // pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4);
   // Document document = new Document(pdf);

   // WritableImage snapshot = pdfPane.snapshot(new SnapshotParameters(), null);
   // BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
   // Image image = new Image(ImageDataFactory.create(bufferedImage, null));

   // document.add(image);
   // document.close();
   // } catch (IOException e) {
   // e.printStackTrace();
   // }
   // return baos.toByteArray();
   // }

   public static byte[] createInvoicePDF(Invoice invoice) throws Exception {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final Object lock = new Object();

      Platform.runLater(() -> {
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                  Objects.requireNonNull(InvoicePDF.class.getResource("printInvoice.fxml")));
            StackPane stackPane = fxmlLoader.load();
            InvoicePDF invoicePDF = fxmlLoader.getController();
            invoicePDF.setData(invoice);

            AnchorPane originalPdfPane = null;
            for (Node node : stackPane.getChildren()) {
               if (node instanceof AnchorPane) {
                  originalPdfPane = (AnchorPane) node;
                  break;
               }
            }

            if (originalPdfPane == null) {
               throw new RuntimeException("AnchorPane within stackPane not found!");
            }

            AnchorPane pdfPane = new AnchorPane();
            pdfPane.getChildren().addAll(originalPdfPane.getChildren());

            Scene tempScene = new Scene(pdfPane);
            tempScene.getRoot().applyCss();
            tempScene.getRoot().layout();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdf);

            WritableImage snapshot = pdfPane.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
            Image image = new Image(ImageDataFactory.create(bufferedImage, null));

            document.add(image);
            document.close();
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            synchronized (lock) {
               lock.notify();
            }
         }
      });

      synchronized (lock) {
         lock.wait();
      }

      return baos.toByteArray();
   }

   public static void sendTelegramDocument(String chatId, byte[] documentBytes, String caption) {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
         HttpPost post = new HttpPost("https://api.telegram.org/bot" + BOT_TOKEN + "/sendDocument");

         MultipartEntityBuilder builder = MultipartEntityBuilder.create();
         builder.addTextBody("chat_id", chatId, ContentType.TEXT_PLAIN);
         builder.addTextBody("caption", caption, ContentType.TEXT_PLAIN);
         builder.addBinaryBody("document", documentBytes, ContentType.APPLICATION_OCTET_STREAM,
               "invoice" + "-" + LocalDate.now() + ".pdf");

         HttpEntity entity = builder.build();
         post.setEntity(entity);

         HttpResponse response = httpClient.execute(post);
         HttpEntity responseEntity = response.getEntity();
         String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
         System.out.println("Telegram API Response: " + responseString);
      } catch (Exception e) {
         return;
      }
   }

   private static File folderRoot() {
      File folder = new File("./src/main/java/com/rentalhouse/app/views/assets/qrcode");
      if (!folder.exists() && !folder.mkdirs()) {
         System.out.println("Failed to create folder!");
         return null;
      }
      System.out.println("Created Folder Successfully!");
      return folder;
   }

   // todo GET DEFAULT USER QR CODE ON DB:
   public static File fileQR(String QRcode) {
      File folder = folderRoot();
      if (folder == null) {
         System.out.println("Folder 'qrcode' not found!");
      }
      if (QRcode != null) {
         String getNameQR = QRcode.substring(QRcode.lastIndexOf('/') + 1);
         return new File(folder, getNameQR);
      }
      return null;
   }

   // todo GET PATH IF USER ADD OTHER QR CODE:
   public static String getQRother() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select you QR Code");
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
      File selectedFile = fileChooser.showOpenDialog(null);

      if (selectedFile != null) {
         return selectedFile.getPath();
      }
      return null;
   }

   // todo SEND QR_CODE DEFAULT ON DB:
   public static void telegramSender(Invoice invoice, User user) {
      String photoPath = "./src/main/java/com/rentalhouse/app/views/assets/images/codeqr.png";
      String QR = user.getBank_QR() == null ? photoPath : user.getBank_QR();
      String bankName = user.getBank_name() == null ? "HSBC" : user.getBank_name();
      String accountNumber = user.getBank_number() == null ? " 000178789999" : user.getBank_number();
      String recipientName = user.getName() == null ? "Ronaldo Nazario Delima" : user.getName();
      String totalPayment = invoice.getTotal_price().toString();
      String infrasName = new InfrastructureController().getById(invoice.getInfrastructureID()).getName();
      String capPDF = String.format("+ PaymentDate: \t%s.\n+ Invoice Detail: \tPDF file Attachment.", LocalDate.now());
      String capQR = String.format("\n+ Bank Name:\t%s\n+ Recipient's Name:\t%s\n+ Account Number:\t%s\n+ Total Payment:\t$%s\n+ Content payment: Payment for infrastructure %s", bankName, recipientName, accountNumber, totalPayment, infrasName);
      try {
         byte[] pdfBytes = createInvoicePDF(invoice);
         sendTelegramDocument(CHAT_ID_A100, pdfBytes, capPDF);
         sendTelegramPhoto(CHAT_ID_A100, QR, capQR);
      } catch (Exception e) {
         return;
      }
   }

   public static void sendingTelegram(Invoice invoice, User user, String infrasName) {
      Platform.runLater(() -> {
         Stage loading = stageLoading();
         Task<Void> sendMessageTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
               if (infrasName.equals("A100")) {
                  telegramSender(invoice, user);
               } else {
                  failed();
               }
               return null;
            }

            @Override
            protected void succeeded() {
               loading.close();
               try {
                  AlertPopup.success("Alert notification", String.format("Invoice address:\t%s\nsended successfully!", invoice.getInvoiveID()));
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }

            @Override
            protected void failed() {
               loading.close();
               try {
                  AlertPopup.error("Alert notification", String.format("Has not been create group for\ninfrastructure %s yet!", infrasName));
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
         };
         loading.show();
         new Thread(sendMessageTask).start();
      });
   }

   private static Stage stageLoading() {
      Stage loading = new Stage();
      try {
         Parent root = FXMLLoader.load(Objects.requireNonNull(LoadingController.class.getResource("loading.fxml")));
         Scene scene = new Scene(root);
         loading.setScene(scene);
         loading.setResizable(false);
         loading.setTitle("Sending notification");
         loading.initStyle(StageStyle.TRANSPARENT);
         loading.setAlwaysOnTop(false);
      } catch (Exception e) {
         e.getMessage();
      }
      return loading;
   }

   public static String qrCodeFile() throws IOException {
      File folder = new File("./src/main/java/com/rentalhouse/app/views/assets/qrcode");
      if (!folder.exists() && !folder.mkdirs()) {
         System.out.println("Failed to create folder!");
         return null;
      }

      File qrCode = new File(folder, "qrCode.png");
      try {
         String qrText = generateBankQRCode("0441000640847", "100000", "Payment for the month's invoice.");
         BufferedImage bufferedImage = generateQRCodeImage(qrText, 300, 300);
         ImageIO.write(bufferedImage, "png", qrCode);
         System.out.println("QR Code generated successfully at: " + qrCode.getPath());
      } catch (Exception e) {
         e.printStackTrace();
      }
      return qrCode.getPath();
   }

   private static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
      QRCodeWriter qrCodeWriter = new QRCodeWriter();
      BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, getHints());
      return MatrixToImageWriter.toBufferedImage(bitMatrix);
   }

   private static String generateBankQRCode(String accountNumber, String amount, String content) {
      String[] str = { "5405", "5406", "5407" };
      String formatAmount = String.format("%010d", Integer.parseInt(amount));
      String qrContent = "000201" +
            "010212" +
            "38570010A00000072701270006970436" +
            "0113" + accountNumber + "0208QRIBFTTA" +
            "5303704" +
            new Random().nextInt(str.length) + formatAmount +
            "5802VN" + // Country code (VN for Vietnam)
            "62190815DT" + content +
            "6304";
      return qrContent;
   }

   private static Map<EncodeHintType, Object> getHints() {
      Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
      hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      return hints;
   }

   public static void main(String[] args) {
      // System.out.println(qrCodeFile());
   }
}
