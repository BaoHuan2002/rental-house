package com.rentalhouse.app.views.components.invoice;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class SinchSmsAPIs {
   private static final String PROJECT_ID = "a49387da-f1fa-4ffb-a50d-be914f7ac87c";
   private static final String API_TOKEN = "e6b132d9-6f12-43cd-bcd0-5c04ba90a822";
   private static final String SINCH_URL = "https://us.sms.api.sinch.com/xms/v1/" + PROJECT_ID + "/batches";

   public static void sendSms(String toPhoneNumber, String messageBody) {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
         HttpPost post = new HttpPost(SINCH_URL);
         post.setHeader("Content-Type", "application/json");
         post.setHeader("Authorization", "Bearer " + API_TOKEN);
         System.out.println(PROJECT_ID);
         System.out.println(API_TOKEN);
         System.out.println(SINCH_URL);
         JSONObject json = new JSONObject();
         json.put("from", "84819099931");
         json.put("to", new String[] { toPhoneNumber });
         json.put("body", messageBody);

         StringEntity entity = new StringEntity(json.toString());
         post.setEntity(entity);

         try (CloseableHttpResponse response = httpClient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println("HTTP Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      sendSms("84971592816", "Hello 1234");
   }
}
