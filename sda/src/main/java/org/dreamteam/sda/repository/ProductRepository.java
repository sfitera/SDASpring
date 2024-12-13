package org.dreamteam.sda.repository;

import org.dreamteam.sda.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,String> {
    List<Product> findByNameContainingIgnoreCase(String name);

}
