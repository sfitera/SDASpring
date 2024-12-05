package org.dreamteam.sda.service;

import org.dreamteam.sda.controller.requet.UpdateClient;
import org.dreamteam.sda.model.Client;

import java.util.List;

public interface ClientService {

    Client addClient(String name, String address);
    Client updateClient(String id, UpdateClient client);
    void deleteClient(String id);
    Client getClient(String id);
    List<Client> getClients();
}
