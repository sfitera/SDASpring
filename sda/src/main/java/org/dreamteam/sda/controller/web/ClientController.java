package org.dreamteam.sda.controller.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.web.request.CreateClient;
import org.dreamteam.sda.controller.web.request.UpdateClient;
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
        setDefaultValues(model);
        model.addAttribute("clients",clientService.getClients());
        model.addAttribute("createClient", new Client());
        return "clients";
    }

    private void setDefaultValues(Model model) {
        model.addAttribute("pageTitle", "Client");
    }

    @PostMapping("/add")
    String addClient(@Valid CreateClient client, BindingResult result, Model model) {
        setDefaultValues(model);
        if (result.hasErrors()) {
            model.addAttribute("clients",clientService.getClients());
            model.addAttribute("createClient", client);
            return "clients";
        }
        clientService.addClient(client.getName(), client.getAddress());
        return "redirect:/clients/";
    }

    @GetMapping("/delete/{id}")
    String deleteClient(@PathVariable String id, Model model) {
        setDefaultValues(model);
        clientService.deleteClient(id);
        return "redirect:/clients/";
    }

    @PostMapping("/update/{id}")
    String updateClient(@PathVariable String id, @Valid UpdateClient client, BindingResult result, Model model) {
        setDefaultValues(model);
        if (result.hasErrors()) {
            client.setId(id);
            model.addAttribute("updateClient", client);
            return "edit_client";
        }
        clientService.updateClient(client.getId(), new Client(client.getId(), client.getName(), client.getAddress()));

        return "redirect:/clients/";
    }

    @GetMapping("/edit/{id}")
    String updateClient(@PathVariable String id, Model model) {
        setDefaultValues(model);
        model.addAttribute("updateClient",clientService.getClient(id));
        return "edit_client";
    }

}
