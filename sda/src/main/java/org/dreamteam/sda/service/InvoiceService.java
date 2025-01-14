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

    InvoiceItem addItemToInvoice(String orderId, @NonNull String productId, int amount);
    List<InvoiceItem> getAllItemsFor(String orderId);
    InvoiceItem getInvoiceItem(String orderId, String id);
    void deleteInvoiceItem(String orderId, String id);
    InvoiceItem updateInvoiceItem(String orderId, String id, String productId, Integer amount);
}
