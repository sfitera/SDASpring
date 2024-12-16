package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Invoice;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InvoiceService {

    Invoice addInvoice (Client client, LocalDate date);
    Invoice updateInvoice (String id, Invoice invoice);
    void deleteInvoice (String id);
    Invoice getInvoice (String id);
    List<Invoice> getInvoices ();


}
