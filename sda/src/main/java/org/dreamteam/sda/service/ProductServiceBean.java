package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.UpdateProduct;
import org.dreamteam.sda.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class ProductServiceBean implements ProductService{

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Product addProduct(String name, String price) {
        Product Product = new Product(UUID.randomUUID().toString(), name, price);
        if(products.containsKey(Product.id())){
            throw new IllegalArgumentException("Product with id " + Product.id() + " already exists");
        }
        products.put(Product.id(), Product);
        log.info("Product with id " + Product.id() + " added");
        return Product;
    }

    @Override
    public Product updateProduct(String id, UpdateProduct updateProduct) {
        if(!products.containsKey(id)){
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        var Product = products.get(id);
        var builder = Product.builder().id(id);
        if(StringUtils.hasText(updateProduct.name())){
            builder.name(updateProduct.name());
            } else {
            builder.name(Product.name());
        }
        if(StringUtils.hasText(updateProduct.price())){
            builder.price(updateProduct.price());
        } else {
            builder.price(Product.price());
        }
        var updated = builder.build();
        products.put(id, updated);
        log.info("Product with id " + Product.id() + " updated");
        return updated;
    }

    @Override
    public void deleteProduct(String id) {
        if(!products.containsKey(id)){
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        products.remove(id);
        log.info("Product with id " + id + " deleted");
    }

    @Override
    public Product getProduct(String id) {
        if(!products.containsKey(id)){
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        return products.get(id);

    }

    @Override
    public List<Product> getProducts() {
       if(products.isEmpty()){
           return null;
       }
       return new ArrayList<>(products.values());

    }
}
