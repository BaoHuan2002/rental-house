package com.rentalhouse.app.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.repositories.InvoiceRepository;
public class InvoiceService implements IGenericService<Invoice> {

   private static final InvoiceRepository invoiceRepository = new InvoiceRepository();

   @Override
   public List<Invoice> getAll() {
      return invoiceRepository.getAll();
   }

   @Override
   public Invoice getById(String id) {
      return invoiceRepository.getById(id);
   }

   @Override
   public boolean create(Invoice entity) {
      return invoiceRepository.create(entity);
   }

   @Override
   public boolean update(Invoice entity) {
      return invoiceRepository.update(entity);
   }

   @Override
   public boolean delete(String id) {
      return invoiceRepository.delete(id);
   }

   public Boolean isExisted(Invoice invoice) {
      return invoiceRepository.isExisted(invoice);
   }

   public Invoice getInvoice(String infrastructureID) {
      return invoiceRepository.getInvoice(infrastructureID);
   }

   public List<Invoice> getInvoices(String keyword, String infrasID) {
      return invoiceRepository.getInvoices(keyword, infrasID);
   }

   public void updateStatus(String invoiceID) {
      invoiceRepository.updateStatus(invoiceID);
   }

   public Map<String, BigDecimal> getRevenueByMonths(String userID) {
      return invoiceRepository.getRevenueByMonths(userID);
   }

   public BigDecimal getTotalYear(Integer year, String userID) {
      return invoiceRepository.getTotalYear(year, userID);
   }

   public Map<String, BigDecimal> getMonthBySearch(Integer year, String userID) {
      return invoiceRepository.getMonthBySearch(year, userID);
   }

   public List<Invoice> getInvoicesByMonth(String date, String userID) {
      return invoiceRepository.getInvoicesByMonth(date, userID);
   }

   // HANDLE VALIDATE INPUT FIELDS:
   public Boolean validateInputFields(Invoice invoice) {
      String regex = "[\\d\\.]+";
      String regexIndex = "[\\d\\]+";
      if (invoice.getPrice().isEmpty() || invoice.getWater_price().isEmpty() || invoice.getElectricity_price().isEmpty()
            ||
            invoice.getOldElectricity_number().isEmpty() || invoice.getNewElectricity_number().isEmpty()
            || invoice.getOldWater_number().isEmpty() ||
            invoice.getNewWater_number().isEmpty() || invoice.getTotal_price().isEmpty()) {
         return false;
      } else if (!invoice.getPrice().matches(regex) || !invoice.getWater_price().matches(regex) ||
            !invoice.getElectricity_price().matches(regex) || !invoice.getTotal_price().matches(regex)) {
         return false;
      } else if (!invoice.getOldElectricity_number().matches(regexIndex)
            || !invoice.getNewElectricity_number().matches(regexIndex) ||
            !invoice.getOldWater_number().matches(regexIndex) || !invoice.getNewWater_number().matches(regexIndex)) {
         return false;
      }
      return true;
   }

   public static void main(String[] args) {
      // todo;
   }
}
