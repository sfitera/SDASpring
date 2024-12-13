package org.dreamteam.sda.controller.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.UpdateClient;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    String getAllClients(Model model) {
        model.addAttribute("clients",clientService.getClients());
        model.addAttribute("client", Client.builder().build());
        return "clients";
    }

    @PostMapping("/add")
    String addClient(Client client, Model model) {
        clientService.addClient(client.name(),client.address());
        return "redirect:/clients/";
    }

    @GetMapping("/delete/{id}")
    String deleteClient(@PathVariable String id, Model model) {
        clientService.deleteClient(id);
        return "redirect:/clients/";
    }

    @PostMapping("/update/{id}")
    String updateClient(@PathVariable String id, @Valid UpdateClient client, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "edit_client";
        }
        clientService.updateClient(id, client);
        return "redirect:/clients/";
    }

    @GetMapping("/edit/{id}")
    String updateClient(@PathVariable String id, Model model) {
        model.addAttribute("client",clientService.getClient(id));
        return "edit_client";
    }

}
