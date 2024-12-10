package org.dreamteam.sda.controller;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.CreateProduct;
import org.dreamteam.sda.controller.requet.UpdateProduct;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    ResponseEntity <Object> addProduct(@RequestBody CreateProduct product) {
        Product created = productService.addProduct(product.name(), product.price());
        //return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.created(URI.create("/products/" + created.id())).body(created);
    }

    @GetMapping("/")
    List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    Product getProduct(@NonNull @PathVariable("id") String id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody UpdateProduct product) {
        var updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();
    }

}
