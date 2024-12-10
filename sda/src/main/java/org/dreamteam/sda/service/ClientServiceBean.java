package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.UpdateClient;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class ClientServiceBean implements ClientService{

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public Client addClient(String name, String address) {
        if(!StringUtils.hasText(name)) {
            throw new NotFoundException("Client name cannot be empty");
        }
        if(!StringUtils.hasText(address)) {
            throw new NotFoundException("Client address cannot be empty");
        }
        Client client = new Client(UUID.randomUUID().toString(), name, address);
        if(clients.containsKey(client.id())){
            throw new IllegalArgumentException("Client with id " + client.id() + " already exists");
        }
        clients.put(client.id(), client);
        log.info("Client with id " + client.id() + " added");
        return client;
    }

    @Override
    public Client updateClient(String id, UpdateClient updateClient) {
        if(!clients.containsKey(id)){
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        var client = clients.get(id);
        var builder = Client.builder().id(id);
        if(StringUtils.hasText(updateClient.name())){
            builder.name(updateClient.name());
            } else {
            builder.name(client.name());
        }
        if(StringUtils.hasText(updateClient.address())){
            builder.address(updateClient.address());
        } else {
            builder.address(client.address());
        }
        var updated = builder.build();
        clients.put(id, updated);
        log.info("Client with id " + client.id() + " updated");
        return updated;
    }

    @Override
    public void deleteClient(String id) {
        if(!clients.containsKey(id)){
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        clients.remove(id);
        log.info("Client with id " + id + " deleted");
    }

    @Override
    public Client getClient(String id) {
        if(!clients.containsKey(id)){
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        return clients.get(id);

    }

    @Override
    public List<Client> getClients() {
       if(clients.isEmpty()){
           return null;
       }
       return new ArrayList<>(clients.values());

    }
}
