package org.dreamteam.sda.service;

import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.repository.ClientRepository;
import org.dreamteam.sda.repository.ProductRepository;
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
class ProductServiceBeanTest {

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        ProductServiceBean productServiceBean(ProductRepository productRepository) {
            return new ProductServiceBean(productRepository);
        }
    }
    @Autowired
    ProductServiceBean productServiceBean;

    @MockitoBean
    ProductRepository productRepository;

    @Test
    void addProduct() {
        String uuid = UUID.randomUUID().toString();
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    product.setId(uuid);
                    return product;
                });

        var product = productServiceBean.addProduct("Laptop", "1500");
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        assertNotNull(product);
        assertEquals(uuid, product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals("1500", product.getPrice());
    }

    @Test
    void updateProduct() {
        String uuid = UUID.randomUUID().toString();
        Product product = new Product(uuid,"Phone","700");
        Product updatedProduct  = new Product(uuid,"SmartPhone","1000");

        Mockito.when(productRepository.existsById(uuid)).thenReturn(true);
        Mockito.when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(updatedProduct );

        var result = productServiceBean.updateProduct(uuid, updatedProduct);
        Mockito.verify(productRepository, Mockito.times(1)).findById(uuid);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        assertNotNull(result);
        assertEquals("SmartPhone", result.getName());
        assertEquals("1000", result.getPrice());

    }

    @Test
    void deleteProduct() {
        String productId = UUID.randomUUID().toString();

        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        Mockito.doNothing().when(productRepository).deleteById(productId);

        productServiceBean.deleteProduct(productId);

        Mockito.verify(productRepository, Mockito.times(1)).existsById(productId);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
    }

    @Test
    void getProduct() {
        String productId = UUID.randomUUID().toString();
        Product product = new Product(productId, "Tablet", "300");

        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        var result = productServiceBean.getProduct(productId);

        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Tablet", result.getName());
        assertEquals("300", result.getPrice());
    }

    @Test
    void getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID().toString(), "Product 1", "100"));
        products.add(new Product(UUID.randomUUID().toString(), "Product 2", "200"));

        Mockito.when(productRepository.findAll()).thenReturn(products);

        var result = productServiceBean.getProducts();

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        assertEquals(2, result.size());
    }
}