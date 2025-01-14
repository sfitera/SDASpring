package org.dreamteam.sda.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.exception.NotFoundException;

import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.model.InvoiceItem;
import org.dreamteam.sda.repository.ClientRepository;
import org.dreamteam.sda.repository.InvoiceItemRepository;
import org.dreamteam.sda.repository.InvoiceRepository;
import org.dreamteam.sda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class InvoiceServiceBean implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public InvoiceServiceBean(InvoiceRepository invoiceRepository, ClientRepository clientRepository,
                              ProductRepository productRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public Invoice addInvoice(String clientId, LocalDate date) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        var invoice = new Invoice(UUID.randomUUID().toString(), client, date);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(String id, String clientId, LocalDate date) {
        var invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found " + id));
        if (StringUtils.hasText(clientId)) {
            var client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Client not found" + clientId));
            invoice.setClient(client);
        }
        if (date != null) {
            invoice.setDate(date);
        }
        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public void deleteInvoice(String id) {
        var invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found " + id));
        invoiceRepository.delete(invoice);
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


    @Override
    public InvoiceItem addItemToInvoice(String invoiceId, @NonNull String productId, int amount) {
        var invoice = getInvoice(invoiceId);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        var invoiceItem = new InvoiceItem(UUID.randomUUID().toString(), invoice, product,amount);
        return invoiceItemRepository.save(invoiceItem);
    }

    @Override
    public List<InvoiceItem> getAllItemsFor(String invoiceId) {
        return invoiceItemRepository.findAllByInvoiceId(invoiceId);
    }

    @Override
    public InvoiceItem getInvoiceItem(String invoiceId, String id) {
        var invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice item not found " + id));
        if (!invoiceItem.getInvoice().getId().equals(invoiceId)) {
            throw new IllegalArgumentException("Invoice item not found in invoice " + invoiceId);
        }
        return invoiceItem;
    }

    @Override
    public void deleteInvoiceItem(String invoiceId, String id) {
        var invoiceItem = getInvoiceItem(invoiceId, id);
        invoiceItemRepository.delete(invoiceItem);
    }

    @Override
    public InvoiceItem updateInvoiceItem(String invoiceId, String id, String productId, Integer amount) {
        var invoiceItem = getInvoiceItem(invoiceId, id);
        if (StringUtils.hasText(productId)) {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
            invoiceItem.setProduct(product);
        }
        if (amount != null) {
            invoiceItem.setAmount(amount);
        }
        return invoiceItemRepository.save(invoiceItem);
    }


}
