package org.dreamteam.sda.controller.rest;

import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientApi.class)
class ClientApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void addClient() throws Exception {
        var client = new Client(UUID.randomUUID().toString(), "John Doe", "123 Main St");

        Mockito.when(clientService.addClient(any(), any())).thenReturn(client);

        mockMvc.perform(post("/rest/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "address": "123 Main St"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/clients/" + client.getId()))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"));

        Mockito.verify(clientService, Mockito.times(1)).addClient("John Doe", "123 Main St");
    }

    @Test
    void getAllClients() throws Exception {
        List<Client> clients = List.of(
                new Client(UUID.randomUUID().toString(),"Client1","Address1"),
                new Client(UUID.randomUUID().toString(),"Client2","Address2")
        );
        Mockito.when(clientService.getClients()).thenReturn(clients);
        mockMvc.perform(get("/rest/clients/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Client1"))
                .andExpect(jsonPath("$[0].address").value("Address1"))
                .andExpect(jsonPath("$[1].name").value("Client2"))
                .andExpect(jsonPath("$[1].address").value("Address2"));

        Mockito.verify(clientService, Mockito.times(1)).getClients();
    }

    @Test
    void getClient() throws Exception {
        String clientId = UUID.randomUUID().toString();
        Client client = new Client(clientId, "Client1", "Address1");

        Mockito.when(clientService.getClient(clientId)).thenReturn(client);

        mockMvc.perform(get("/rest/clients/{id}",clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.name").value("Client1"))
                .andExpect(jsonPath("$.address").value("Address1"));

        Mockito.verify(clientService, Mockito.times(1)).getClient(clientId);
    }

    @Test
    void deleteClient() throws Exception {
        String clientId = UUID.randomUUID().toString();

        Mockito.doNothing().when(clientService).deleteClient(clientId);

        mockMvc.perform(delete("/rest/clients/{id}",clientId))
                .andExpect(status().isNoContent());

        Mockito.verify(clientService, Mockito.times(1)).deleteClient(clientId);

    }

    @Test
    void updateClient() throws Exception {
        String clientId = UUID.randomUUID().toString();
        Client updatedClient = new Client(clientId, "Updated Name", "Updated Address");

        Mockito.when(clientService.updateClient(eq(clientId), any())).thenReturn(updatedClient);

        mockMvc.perform(put("/rest/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Updated Name",
                                    "address": "Updated Address"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.address").value("Updated Address"));

        Mockito.verify(clientService, Mockito.times(1)).updateClient(eq(clientId), any());
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        Mockito.when(clientService.addClient(any(), any())).thenThrow(new IllegalArgumentException("Invalid argument"));

        mockMvc.perform(post("/rest/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "address": "123 Main St"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleNotFoundException() throws Exception {
        String clientId = UUID.randomUUID().toString();

        Mockito.when(clientService.getClient(eq(clientId))).thenThrow(new NotFoundException("Not found"));

        mockMvc.perform(get("/rest/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }
}