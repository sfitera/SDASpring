package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.model.InvoiceItem;
import org.dreamteam.sda.model.Product;
import java.util.List;

public interface InvoiceItemService {

    InvoiceItem addInvoiceItem (Invoice invoice, Product product, int amount);
    InvoiceItem updateInvoiceItem (String id, Invoice invoice, Product product, int amount);
    void deleteInvoiceItem (String id);
    InvoiceItem getInvoiceItem (String id);
    List<InvoiceItem> getInvoiceItems ();


}
