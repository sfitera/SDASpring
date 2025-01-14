package org.dreamteam.sda.controller.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.web.request.CreateInvoice;
import org.dreamteam.sda.controller.web.request.CreateInvoiceItem;
import org.dreamteam.sda.controller.web.request.UpdateInvoice;
import org.dreamteam.sda.service.ClientService;
import org.dreamteam.sda.service.InvoiceService;
import org.dreamteam.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/invoices")
class InvoiceController {

    private final InvoiceService invoiceService;
    private final ClientService clientService;
    private final ProductService productService;

    @Autowired
    InvoiceController(InvoiceService invoiceService, ClientService clientService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping("/")
    String getAllInvoices(Model model) {
        setDefaultValues(model);
        model.addAttribute("invoices",invoiceService.getInvoices());
        model.addAttribute("createInvoice", new CreateInvoice());
        model.addAttribute("clientList", clientService.getClients());
        return "invoices";
    }

    private void setDefaultValues(Model model) {
        model.addAttribute("pageTitle", "Invoices");
    }

    @PostMapping("/add")
    String addInvoice(@Valid CreateInvoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("invoices",invoiceService.getInvoices());
            model.addAttribute("createInvoice", invoice);
            model.addAttribute("clientList", clientService.getClients());
            return "invoices";
        }
        invoiceService.addInvoice(invoice.getClientID(), invoice.getDate());
        return "redirect:/invoices/";
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

    @GetMapping("/delete/{id}")
    String deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices/";
    }

    @PostMapping("/update")
    String updateInvoice(@Valid UpdateInvoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientList", clientService.getClients());
            model.addAttribute("updateInvoice", invoice);
            return "edit_invoice";
        }
        invoiceService.updateInvoice(invoice.invoiceId(),invoice.clientId(),invoice.date());

        return "redirect:/invoices/";
    }

    @GetMapping("/edit/{id}")
    String updateInvoice(@PathVariable("id") String id, Model model) {
        setDefaultValues(model);
        var invoice = invoiceService.getInvoice(id);
        model.addAttribute("updateInvoice", new UpdateInvoice(invoice.getId(),invoice.getClient().getId(),invoice.getDate()));
        model.addAttribute("clientList", clientService.getClients());
        model.addAttribute("orderItems", invoiceService.getAllItemsFor(id));
        model.addAttribute("createOrderItem", new CreateInvoiceItem());
        model.addAttribute("productList", productService.getProducts());
        return "edit_invoice";
    }

    @PostMapping("/{id}/items/add")
    public String addInvoiceItem(@PathVariable("id") String invoiceId, CreateInvoiceItem invoiceItem, Model model) {
        setDefaultValues(model);
        invoiceService.addItemToInvoice(invoiceId, invoiceItem.getProductId(), invoiceItem.getAmount());
        return "redirect:/orders/edit/" + invoiceId;
    }
    @GetMapping("/{orderId}/items/{itemId}/delete")
    public String deleteInvoiceItem(@PathVariable("orderId") String invoiceId, @PathVariable("itemId") String itemId, Model model) {
        setDefaultValues(model);
        invoiceService.deleteInvoiceItem(invoiceId, itemId);
        return "redirect:/orders/edit/" + invoiceId;
    }

}
