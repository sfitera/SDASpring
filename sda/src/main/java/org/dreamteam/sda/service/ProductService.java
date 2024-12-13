package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(String name, String price);
    Product updateProduct(String id, Product Product);
    void deleteProduct(String id);
    Product getProduct(String id);
    List<Product> getProducts();
}
