package org.dreamteam.sda.controller;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.CreateClient;
import org.dreamteam.sda.controller.requet.UpdateClient;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/")
    ResponseEntity <Object> addClient(@RequestBody CreateClient client) {
        Client created = clientService.addClient(client.name(), client.address());
        //return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.created(URI.create("/clients/" + created.id())).body(created);
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
    ResponseEntity<Object> deleteClient(@PathVariable("id") String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Client> updateClient(@PathVariable("id") String id, @RequestBody UpdateClient client) {
        var updated = clientService.updateClient(id, client);
        return ResponseEntity.ok(updated);
    }

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
