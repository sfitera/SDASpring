package org.dreamteam.sda.controller.rest;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.rest.request.CreateInvoice;
import org.dreamteam.sda.controller.rest.request.UpdateInvoice;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.service.ClientService;
import org.dreamteam.sda.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/invoices")
class InvoiceApi {

    private final InvoiceService invoiceService;

    @Autowired
    InvoiceApi(InvoiceService invoiceService, ClientService clientService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/")
    ResponseEntity <Object> addInvoice(@RequestBody CreateInvoice invoice) {
        Invoice created = invoiceService.addInvoice(invoice.clientId(), invoice.date());
        //return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.created(URI.create("/invoices/" + created.getId())).body(created);
    }

    @GetMapping("/")
    List<Invoice> getAllInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping("/{id}")
    Invoice getInvoice(@NonNull @PathVariable("id") String id) {
        return invoiceService.getInvoice(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteInvoice(@PathVariable("id") String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Invoice> updateInvoice(@PathVariable("id") String id, @RequestBody UpdateInvoice invoice) {
        var updated = invoiceService.updateInvoice(id, invoice.clientId(), invoice.date());
        return ResponseEntity.ok(updated);
    }

//    private LocalDate parseDate(@NotBlank(message = "Date is required") String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        try {
//            return LocalDate.parse(date, formatter);
//        } catch (DateTimeParseException e) {
//            System.err.println("Invalid date format: " + e.getMessage());
//            return null;
//        }
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();
    }
}
