package org.dreamteam.sda.repository;

import org.dreamteam.sda.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client,String> {
    List<Client> findByNameContainingIgnoreCase(String name);

}
