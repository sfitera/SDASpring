package org.dreamteam.sda.controller.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.web.request.CreateInvoice;
import org.dreamteam.sda.controller.web.request.UpdateInvoice;
import org.dreamteam.sda.model.Invoice;
import org.dreamteam.sda.service.ClientService;
import org.dreamteam.sda.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/invoices")
class InvoiceController {

    private final InvoiceService invoiceService;
    private final ClientService clientService;

    @Autowired
    InvoiceController(InvoiceService invoiceService, ClientService clientService) {
        this.invoiceService = invoiceService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    String getAllInvoices(Model model) {
        model.addAttribute("invoices",invoiceService.getInvoices());
        model.addAttribute("createInvoice", new Invoice());
        model.addAttribute("clientList", clientService.getClients());
        return "invoices";
    }

    @PostMapping("/add")
    String addInvoices(@Valid CreateInvoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("invoices",invoiceService.getInvoices());
            model.addAttribute("createInvoice", invoice);
            return "invoices";
        }
        invoiceService.addInvoice(invoice.getClient(), parseDate(invoice.getDate()));
        return "redirect:/invoices/";
    }

    private LocalDate parseDate(@NotBlank(message = "Date is required") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/delete/{id}")
    String deleteInvoice(@PathVariable String id, Model model) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices/";
    }

    @PostMapping("/update/{id}")
    String updateInvoice(@PathVariable String id, @Valid UpdateInvoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            invoice.setId(id);
            model.addAttribute("updateInvoice", invoice);
            return "edit_invoice";
        }
        invoiceService.updateInvoice(invoice.getId(), new Invoice(invoice.getId(), invoice.getClient(), invoice.getDate()));

        return "redirect:/invoices/";
    }

    @GetMapping("/edit/{id}")
    String updateInvoice(@PathVariable String id, Model model) {
        model.addAttribute("updateInvoice",invoiceService.getInvoice(id));
        return "edit_invoice";
    }

}
