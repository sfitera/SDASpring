package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class InvoiceServiceBean implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceBean(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice addInvoice(Client client, LocalDate date) {
        Invoice invoice = new Invoice(UUID.randomUUID().toString(),client,date);
        if (invoiceRepository.existsById(client.getId())) {
            throw new IllegalArgumentException("Client with id " + client.getId() + " already exists");
        }
        invoiceRepository.save(invoice);
        log.info("Invoice added: {}", invoice);
        return invoice;
    }

    @Override
    public Invoice updateInvoice(String id, Invoice updateInvoice) {
        var client = invoiceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " does not exist"));
        var updated = new Invoice();
        if (updateInvoice.getClient()!=null) {
            updated.setClient(updateInvoice.getClient());
        } else {
            updated.setClient(client.getClient());
        }
        if (updateInvoice.getDate()!=null) {
            updated.setDate(updateInvoice.getDate());
        } else {
            updated.setDate(client.getDate());
        }
        invoiceRepository.save(updated);
        log.info("Invoice updated: {}", updated);
        return updated;
    }

    @Override
    public void deleteInvoice(String id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException("Invoice with id " + id + " does not exist");
        }
        invoiceRepository.deleteById(id);
        log.info("Invoice with id " + id + " deleted");
    }

    @Override
    public Invoice getInvoice(String id) {
        return invoiceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " does not exist"));
    }

    @Override
    public List<Invoice> getInvoices() {
        return StreamSupport.stream(invoiceRepository.findAll().spliterator(), false).toList();
    }




}
