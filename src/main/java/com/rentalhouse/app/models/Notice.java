package com.rentalhouse.app.models;

import java.sql.Date;

public class Notice {
   private int noticeID;
   private String title;
   private String amount_received;
   private String remaining_amount;
   private String invoiceID;
   private String description;
   private int status;
   private Date create_date;
   private Date update_date;

   // CONSTRUCTOR GET NOTICE:
   public Notice(int id, String title, String amount_received, String remaining_amount, String invoiceID, String description,
                  int status, Date create_date, Date update_date) {
      this.noticeID = id;
      this.title = title;
      this.amount_received = amount_received;
      this.remaining_amount = remaining_amount;
      this.invoiceID = invoiceID;
      this.description = description;
      this.status = status;
      this.create_date = create_date;
      this.update_date = update_date;
   }

   // CONSTRUCTOR CREATE NOTICE:
   public Notice(String title, String amount_received, String remaining_amount, String invoiceID, String description) {
      this.title = title;
      this.amount_received = amount_received;
      this.remaining_amount = remaining_amount;
      this.invoiceID = invoiceID;
      this.description = description;
   }

   public int getNoticeID() {
      return noticeID;
   }

   public void setNoticeID(int noticeID) {
      this.noticeID = noticeID;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAmount_received() {
      return amount_received;
   }

   public void setAmount_received(String amount_received) {
      this.amount_received = amount_received;
   }

   public String getRemaining_amount() {
      return remaining_amount;
   }

   public void setRemaining_amount(String remaining_amount) {
      this.remaining_amount = remaining_amount;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getInvoiceID() {
      return invoiceID;
   }

   public void setInvoiceID(String invoiceID) {
      this.invoiceID = invoiceID;
   }

   public Date getCreate_date() {
      return create_date;
   }

   public void setCreate_date(Date create_date) {
      this.create_date = create_date;
   }

   public Date getUpdate_date() {
      return update_date;
   }

   public void setUpdate_date(Date update_date) {
      this.update_date = update_date;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   

   @Override
   public String toString() {
      return "Notice [noticeID=" + noticeID + ", title=" + title + ", amount_received=" + amount_received
            + ", remaining_amount=" + remaining_amount + ", description=" + description + ", invoiceID=" + invoiceID
            + ", create_date=" + create_date + ", update_date=" + update_date + "]";
   }
}
