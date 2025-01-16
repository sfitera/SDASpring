package org.dreamteam.sda.controller.rest;

import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.service.ProductService;
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


@WebMvcTest(ProductApi.class)
class ProductApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void addProduct() throws Exception {
        var product = new Product(UUID.randomUUID().toString(), "Produkt","1000");

        Mockito.when(productService.addProduct(any(String.class), any(String.class))).thenReturn(product);
        mockMvc.perform(post("/rest/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Produkt",
                                    "price": "1000"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/products/" + product.getId()))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value("Produkt"))
                .andExpect(jsonPath("$.price").value("1000"));

        Mockito.verify(productService, Mockito.times(1)).addProduct("Produkt", "1000");
    }

    @Test
    void getAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product(UUID.randomUUID().toString(), "Product1","1000"),
                new Product(UUID.randomUUID().toString(), "Product2","1500")
        );

        Mockito.when(productService.getProducts()).thenReturn(products);
        mockMvc.perform(get("/rest/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[0].price").value("1000"))
                .andExpect(jsonPath("$[1].name").value("Product2"))
                .andExpect(jsonPath("$[1].price").value("1500"));

        Mockito.verify(productService, Mockito.times(1)).getProducts();

    }

    @Test
    void getProduct() throws Exception {
        String productId = UUID.randomUUID().toString();
        Product product = new Product(productId, "Tablet", "500");

        Mockito.when(productService.getProduct(eq(productId))).thenReturn(product);

        mockMvc.perform(get("/rest/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Tablet"))
                .andExpect(jsonPath("$.price").value("500"));

        Mockito.verify(productService, Mockito.times(1)).getProduct(productId);
    }

    @Test
    void deleteProduct() throws Exception {
        String productId = UUID.randomUUID().toString();

        Mockito.doNothing().when(productService).deleteProduct(eq(productId));

        mockMvc.perform(delete("/rest/products/{id}", productId))
                .andExpect(status().isNoContent());

        Mockito.verify(productService, Mockito.times(1)).deleteProduct(productId);
    }

    @Test
    void updateProduct() throws Exception {
        String productId = UUID.randomUUID().toString();
        Product updatedProduct = new Product(productId, "Updated Product", "2500");

        Mockito.when(productService.updateProduct(eq(productId), any())).thenReturn(updatedProduct);

        mockMvc.perform(put("/rest/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Updated Product",
                                    "price": "2500"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value("2500"));

        Mockito.verify(productService, Mockito.times(1)).updateProduct(eq(productId), any());
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        Mockito.when(productService.addProduct(any(), any())).thenThrow(new IllegalArgumentException("Invalid argument"));

        mockMvc.perform(post("/rest/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "price": "100"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleNotFoundException() throws Exception {
        String productId = UUID.randomUUID().toString();

        Mockito.when(productService.getProduct(eq(productId))).thenThrow(new NotFoundException("Not found"));

        mockMvc.perform(get("/rest/products/{id}", productId))
                .andExpect(status().isNotFound());
    }
}