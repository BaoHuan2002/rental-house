package com.rentalhouse.app.services;

import java.util.List;

import com.rentalhouse.app.models.Notice;
import com.rentalhouse.app.repositories.NoticeRepository;

public class NoticeService implements IGenericService<Notice> {
   private NoticeRepository noticeRepository = new NoticeRepository();

   @Override
   public List<Notice> getAll() {
      return null;
   };

   @Override
   public Notice getById(String id) {
      return null;
   };

   @Override
   public boolean create(Notice notice) {
      return noticeRepository.create(notice);
   };

   public List<Notice> getNotices(String invoiceID) {
      return noticeRepository.getNotices(invoiceID);
   }

   @Override
   public boolean update(Notice entity) {
      return false;
   };

   @Override
   public boolean delete(String id) {
      return false;
   };

   public Notice getNotice(Notice notice) {
      return noticeRepository.getNotice(notice);
   }

   public Boolean delete(Notice notice) {
      return noticeRepository.delete(notice);
   }

   public List<Notice> getList(String keyword, String invoiceID) {
      return noticeRepository.getList(keyword, invoiceID);
   }

   public Boolean existNotice(String invoiceID) {
      return noticeRepository.existNotice(invoiceID);
   }

   public Notice getNotice(String invoiceID) {
      return noticeRepository.getNotice(invoiceID);
   }

   public Boolean updateStatus(Notice notice, int status) {
      return noticeRepository.updateStatus(notice, status);
   }

   public Integer getCountUnProcess(String invoiceID, int status) {
      return noticeRepository.getCountUnProcess(invoiceID, status);
   }

   public Boolean deleteNotices(List<Notice> notices) {
      return noticeRepository.deleteNotices(notices);
   }
}
