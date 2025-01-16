package org.dreamteam.sda.controller.rest;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.rest.request.CreateInvoiceItem;
import org.dreamteam.sda.controller.rest.request.UpdateInvoiceItem;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.InvoiceItem;
import org.dreamteam.sda.service.ClientService;
import org.dreamteam.sda.service.InvoiceItemService;
import org.dreamteam.sda.service.InvoiceService;
import org.dreamteam.sda.service.ProductService;
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
@RequestMapping("/rest/invoice/{invoiceId}/items")
class InvoiceItemApi {

    private final InvoiceService invoiceService;


    @Autowired
    InvoiceItemApi(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;

    }

    @PostMapping("/add")
    ResponseEntity<InvoiceItem> addInvoiceItem(@NonNull @PathVariable("invoiceId") String invoiceId, @RequestBody CreateInvoiceItem invoiceItem) {
        InvoiceItem created = invoiceService.addItemToInvoice(invoiceId, invoiceItem.productId(), invoiceItem.amount());
        return ResponseEntity.created(
                URI.create(String.format("/invoice/%s/items/%s", created.getInvoice().getId(), created.getId()))
        ).body(created);
    }

    @GetMapping("/")
    List<InvoiceItem> getAllInvoiceItems(@NonNull @PathVariable("invoiceId") String invoiceId) {
        return invoiceService.getAllItemsFor(invoiceId);
    }

    @GetMapping("/{id}")
    InvoiceItem getInvoiceItem(@NonNull @PathVariable("invoiceId") String invoiceId, @NonNull @PathVariable("id") String id) {
        return invoiceService.getInvoiceItem(invoiceId, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteInvoiceItem(@NonNull @PathVariable("invoiceId") String invoiceId, @PathVariable("id") String id) {
        invoiceService.deleteInvoiceItem(invoiceId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<InvoiceItem> updateInvoiceItem(@NonNull @PathVariable("invoiceId") String invoiceId, @PathVariable("id") String id, @RequestBody UpdateInvoiceItem invoice) {
        var updated = invoiceService.updateInvoiceItem(invoiceId,id,invoice.productId(),invoice.amount());
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
