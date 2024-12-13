package org.dreamteam.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProductServiceBean implements ProductService{

    private final ProductRepository productRepository;
    @Autowired
    ProductServiceBean(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(String name, String price) {
        if(!StringUtils.hasText(name)) {
            throw new NotFoundException("Product name cannot be empty");
        }
        if(!StringUtils.hasText(price)) {
            throw new NotFoundException("Product price cannot be empty");
        }
        var product = new Product(UUID.randomUUID().toString(), name, price);
        if (productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Client with id " + product.getId() + " already exists");
        }
        productRepository.save(product);
        log.info("Product added: {}", product);
        return product;
    }

    @Override
    public Product updateProduct(String id, Product updateProduct) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Product with id " + id + " does not exist");
        }
        var product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " does not exist"));
        var updated = new Product();
        if (StringUtils.hasText(updateProduct.getName())) {
            updated.setName(updateProduct.getName());
            } else {
            updated.setName(product.getName());
        }
        if (StringUtils.hasText(updateProduct.getPrice())) {
            updated.setPrice(updateProduct.getPrice());
        } else {
            updated.setPrice(updateProduct.getPrice());
        }
        productRepository.save(updated);
        log.info("Product updated: {}", updated);
        return updated;
    }

    @Override
    public void deleteProduct(String id) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Product with id " + id + " does not exist");
        }
        productRepository.deleteById(id);
        log.info("Product with id " + id + " deleted");
    }

    @Override
    public Product getProduct(String id) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Product with id " + id + " does not exist");
        }
        return productRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Product with id " + id + " does not exist"));

    }

    @Override
    public List<Product> getProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(),false).toList();

    }
}
