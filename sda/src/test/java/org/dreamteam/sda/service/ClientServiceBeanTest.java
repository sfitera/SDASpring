package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class ClientServiceBeanTest {

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        ClientServiceBean clientServiceBean(ClientRepository clientRepository) {
            return new ClientServiceBean(clientRepository);
        }
    }

    @Autowired
    ClientServiceBean clientServiceBean;

    @MockitoBean
    ClientRepository clientRepository;


    @Test
    void addClient() {

        String uuid = UUID.randomUUID().toString();
        Mockito.when(clientRepository.save(Mockito.any(Client.class)))
                .thenAnswer(invocationOnMock -> {
                    Client client = invocationOnMock.getArgument(0);
                    client.setId(uuid);
                    return client;
                });

        var client = clientServiceBean.addClient("Jozko", "ruzova" );
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
        Assertions.assertNotNull(client);
    }

    @Test
    void updateClient() {
        String uuid = UUID.randomUUID().toString();
        Client client = new Client(uuid,"Jozko", "ruzova" );
        Client updatedClient = new Client(uuid, "Jozko Novy","fialova");

        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(updatedClient);

        var result = clientServiceBean.updateClient(uuid, updatedClient);
        Mockito.verify(clientRepository, Mockito.times(1)).findById(uuid);
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
        assertEquals("Jozko Novy", result.getName());
        assertEquals("fialova",result.getAddress());
    }

    @Test
    void deleteClient() {
        String clientId = UUID.randomUUID().toString();

        Mockito.when(clientRepository.existsById(clientId)).thenReturn(true);
        Mockito.doNothing().when(clientRepository).deleteById(clientId);

        clientServiceBean.deleteClient(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).existsById(clientId);
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(clientId);
    }

    @Test
    void getClient() {
        String clientId = UUID.randomUUID().toString();
        Client client = new Client(clientId, "Test Name", "Test Address");

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        var result = clientServiceBean.getClient(clientId);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("Test Name", result.getName());
        assertEquals("Test Address", result.getAddress());
    }

    @Test
    void getClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(UUID.randomUUID().toString(), "Client 1", "Address 1"));
        clients.add(new Client(UUID.randomUUID().toString(), "Client 2", "Address 2"));

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        var result = clientServiceBean.getClients();

        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
        assertEquals(2, result.size());
    }
}