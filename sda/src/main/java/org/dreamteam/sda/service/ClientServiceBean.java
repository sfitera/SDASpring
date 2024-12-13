package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ClientServiceBean implements ClientService{

    private final ClientRepository clientRepository;

    @Autowired
    ClientServiceBean(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(String name, String address) {
        if(!StringUtils.hasText(name)) {
            throw new NotFoundException("Client name cannot be empty");
        }
        if(!StringUtils.hasText(address)) {
            throw new NotFoundException("Client address cannot be empty");
        }
        Client client = new Client(UUID.randomUUID().toString(), name, address);
        if (clientRepository.existsById(client.getId())) {
            throw new IllegalArgumentException("Client with id " + client.getId() + " already exists");
        }
        clientRepository.save(client);
        log.info("Client added: {}", client);
        return client;
    }

    @Override
    public Client updateClient(String id, Client updateClient) {
        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " does not exist"));
        var updated = new Client();
        if (StringUtils.hasText(updateClient.getName())) {
            updated.setName(updateClient.getName());
            } else {
            updated.setName(client.getName());
        }
        if (StringUtils.hasText(updateClient.getAddress())) {
            updated.setAddress(updateClient.getAddress());
        } else {
            updated.setAddress(client.getAddress());
        }
        clientRepository.save(updated);
        log.info("Client updated: {}", updated);
        return updated;
    }

    @Override
    public void deleteClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        clientRepository.deleteById(id);
        log.info("Client with id " + id + " deleted");
    }

    @Override
    public Client getClient(String id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " does not exist"));

    }

    @Override
    public List<Client> getClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).toList();

    }
}
