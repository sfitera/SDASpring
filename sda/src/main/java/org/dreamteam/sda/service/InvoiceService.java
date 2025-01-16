package org.dreamteam.sda.service;

import lombok.NonNull;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.model.InvoiceItem;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InvoiceService {

    Invoice addInvoice (String clientId, LocalDate date);
    void deleteInvoice (String id);
    Invoice getInvoice (String id);
    List<Invoice> getInvoices ();
    Invoice updateInvoice(String id, String clientId, LocalDate localDate);

    InvoiceItem addItemToInvoice(String invoiceId, @NonNull String productId, int amount);
    List<InvoiceItem> getAllItemsFor(String invoiceId);
    InvoiceItem getInvoiceItem(String invoiceId, String id);
    void deleteInvoiceItem(String invoiceId, String id);
    InvoiceItem updateInvoiceItem(String invoiceId, String id, String productId, Integer amount);
}
