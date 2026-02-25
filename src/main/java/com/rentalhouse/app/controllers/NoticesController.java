package com.rentalhouse.app.controllers;
import java.util.List;

import com.rentalhouse.app.models.Notice;
import com.rentalhouse.app.services.NoticeService;

public class NoticesController {
   private NoticeService noticeService = new NoticeService();

   public List<Notice> getNotices(String invoiceID) {
      return noticeService.getNotices(invoiceID);
   }

   public Boolean create(Notice notice) {
      return noticeService.create(notice);
   }

   public Notice getNotice(Notice notice) {
      return noticeService.getNotice(notice);
   }

   public Boolean delete(Notice notice) {
      return noticeService.delete(notice);
   }

   public List<Notice> getList(String keyword, String invoiceID) {
      return noticeService.getList(keyword, invoiceID);
   }

   public Boolean existNotice(String invoiceID) {
      return noticeService.existNotice(invoiceID);
   }

   public Notice getNotice(String invoiceID) {
      return noticeService.getNotice(invoiceID);
   }

   public Boolean updateStatus(Notice notice, int status) {
      return noticeService.updateStatus(notice, status);
   }

   public Integer getCountUnProcess(String invoiceID, int status) {
      return noticeService.getCountUnProcess(invoiceID, status);
   }

   public Boolean deleteNotices(List<Notice> notices) {
      return noticeService.deleteNotices(notices);
   }
}
