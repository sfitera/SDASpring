package org.dreamteam.sda.repository;

import org.dreamteam.sda.model.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice,String> {
    List<Invoice> findByClientNameIgnoreCase(String name);
    List<Invoice> findByClientId(String clientId);

}
