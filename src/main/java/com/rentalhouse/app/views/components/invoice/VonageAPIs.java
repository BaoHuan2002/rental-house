package com.rentalhouse.app.views.components.invoice;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class VonageAPIs {
   private static String API_KEY = "d123d993";
   private static String API_SECRET = "lPvIdrvIVuufmXg1";
   private static String FROM_PHONE_NUMBER = "84819099931";

   public static void senderSMS() {
      VonageClient client = VonageClient.builder()
            .apiKey(API_KEY)
            .apiSecret(API_SECRET)
            .build();

      String toPhoneNumber = "84971592816";
      String messageBody = "Hello 1234";
      TextMessage message = new TextMessage(FROM_PHONE_NUMBER, toPhoneNumber, messageBody);

      try {
         SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
         response.getMessages().forEach(msg -> {
            System.out.println("Message ID: " + msg.getId());
            System.out.println("Status: " + msg.getStatus());
            System.out.println("Error Text: " + msg.getErrorText());
            System.out.println("Remaining Balance: " + msg.getRemainingBalance());
            System.out.println("Message Price: " + msg.getMessagePrice());
            System.out.println("Network: " + msg.getNetwork());
         });

         if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
         } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      senderSMS();
   }
}
