package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceBean implements ClientService{

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public void addClient(Client client) {
        if(clients.containsKey(client.id())){
            throw new IllegalArgumentException("Client with id " + client.id() + " already exists");
        }
        clients.put(client.id(), client);
    }

    @Override
    public void updateClient(Client client) {
        if(!clients.containsKey(client.id())){
            throw new IllegalArgumentException("Client with id " + client.id() + " does not exist");
        }
        clients.put(client.id(), client);

    }

    @Override
    public void deleteClient(String id) {
        if(!clients.containsKey(id)){
            throw new IllegalArgumentException("Client with id " + id + " does not exist");
        }
        clients.remove(id);

    }

    @Override
    public Client getClient(String id) {
        if(!clients.containsKey(id)){
            throw new IllegalArgumentException("Client with id " + id + " does not exist");
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
