package org.dreamteam.sda.repository;

import org.dreamteam.sda.model.InvoiceItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceItemRepository extends CrudRepository<InvoiceItem,String> {
    List<InvoiceItem> findAllByInvoiceId(String orderId);

}
