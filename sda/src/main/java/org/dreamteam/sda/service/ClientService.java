package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Client;

import java.util.List;

public interface ClientService {

    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(String id);
    Client getClient(String id);
    List<Client> getClients();
}
