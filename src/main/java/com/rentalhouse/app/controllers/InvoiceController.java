package com.rentalhouse.app.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.services.InvoiceService;

public class InvoiceController {
   private InvoiceService invoiceService;

   public InvoiceController() {
      invoiceService = new InvoiceService();
   }

   public Boolean create(Invoice invoice) {
      return invoiceService.create(invoice);
   }

   public List<Invoice> getAll() {
      return invoiceService.getAll();
   }

   public Boolean update(Invoice invoice) {
      return invoiceService.update(invoice);
   }

   public Invoice getById(String ID) {
      return invoiceService.getById(ID);
   }

   public Boolean isExisted(Invoice invoice) {
      return invoiceService.isExisted(invoice);
   }

   public Invoice getInvoice(String infrastructureID) {
      return invoiceService.getInvoice(infrastructureID);
   }

   public List<Invoice> getInvoices(String keyword, String infrasID) {
      return invoiceService.getInvoices(keyword, infrasID);
   }

   public void updateStatus(String invoiceID) {
      invoiceService.updateStatus(invoiceID);
   }

   public Map<String, BigDecimal> getRevenueByMonths(String userID) {
      return invoiceService.getRevenueByMonths(userID);
   }

   public BigDecimal getTotalYear(Integer year, String userID) {
      return invoiceService.getTotalYear(year, userID);
   }

   public Map<String, BigDecimal> getMonthBySearch(Integer year, String userID) {
      return invoiceService.getMonthBySearch(year, userID);
   }

   public List<Invoice> getInvoicesByMonth(String date, String userID) {
      return invoiceService.getInvoicesByMonth(date, userID);
   }
}
