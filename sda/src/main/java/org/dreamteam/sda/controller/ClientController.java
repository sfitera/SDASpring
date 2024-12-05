package org.dreamteam.sda.controller;

import io.micrometer.common.lang.NonNull;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/")
    void addClient(Client client) {
        clientService.addClient(client);
    }

    @GetMapping("/")
    List<Client> getAllClients() {
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    Client getClient(@NonNull @PathVariable("id") String id) {
        return clientService.getClient(id);
    }

    @DeleteMapping("/{id}")
    void removeClient(@PathVariable("id") String id) {
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    void updateClient(@PathVariable("id") String id, Client client) {
        clientService.updateClient(client);
    }

}
