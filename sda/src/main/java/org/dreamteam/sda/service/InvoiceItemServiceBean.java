package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.model.InvoiceItem;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.repository.InvoiceItemRepository;
import org.dreamteam.sda.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class InvoiceItemServiceBean implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceBean(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public InvoiceItem addInvoiceItem(Invoice invoice, Product product, int amount) {
        InvoiceItem invoiceItem = new InvoiceItem(UUID.randomUUID().toString(),invoice,product,amount);
        if (invoiceItemRepository.existsById(invoice.getId())) {
            throw new IllegalArgumentException("Client with id " + invoice.getId() + " already exists");
        }
        invoiceItemRepository.save(invoiceItem);
        log.info("Invoice added: {}", invoiceItem);
        return null;
    }

    @Override
    public InvoiceItem updateInvoiceItem(String id, Invoice invoice, Product product, int amount) {
        var invoiceItem = invoiceItemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("InvoiceItem with id " + id + " does not exist"));
        var updated = new InvoiceItem();
        if (invoice!=null) {
            updated.setInvoice(invoice);
        } else {
            updated.setInvoice(invoiceItem.getInvoice());
        }
        if (product!=null) {
            updated.setProduct(product);
        } else {
            updated.setProduct(invoiceItem.getProduct());
        }
        if(amount!=0){
            updated.setAmount(amount);
        }else{
            updated.setAmount(0);
        }

        invoiceItemRepository.save(updated);
        log.info("InvoiceItem updated: {}", updated);
        return updated;
    }

    @Override
    public void deleteInvoiceItem(String id) {
        if (!invoiceItemRepository.existsById(id)) {
            throw new NotFoundException("InvoiceItem with id " + id + " does not exist");
        }
        invoiceItemRepository.deleteById(id);
        log.info("InvoiceItem with id " + id + " deleted");
    }

    @Override
    public InvoiceItem getInvoiceItem(String id) {
        return invoiceItemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("InvoiceItem with id " + id + " does not exist"));
    }

    @Override
    public List<InvoiceItem> getInvoiceItems() {
        return StreamSupport.stream(invoiceItemRepository.findAll().spliterator(), false).toList();
    }
}