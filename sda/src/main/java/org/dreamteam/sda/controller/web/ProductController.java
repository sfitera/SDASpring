package org.dreamteam.sda.controller.web;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.requet.CreateProduct;
import org.dreamteam.sda.controller.requet.UpdateClient;
import org.dreamteam.sda.controller.requet.UpdateProduct;
import org.dreamteam.sda.exception.NotFoundException;
import org.dreamteam.sda.model.Client;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    String getAllProducts(Model model) {
        model.addAttribute("products",productService.getProducts());
        model.addAttribute("product", Product.builder().build());
        return "products";
    }

    @PostMapping("/add")
    String addProduct(Product product, Model model) {
        productService.addProduct(product.name(),product.price());
        return "redirect:/products/";
    }

    @GetMapping("/delete/{id}")
    String deleteProduct(@PathVariable String id, Model model) {
        productService.deleteProduct(id);
        return "redirect:/products/";
    }

    @PostMapping("/update/{id}")
    String updateProduct(@PathVariable String id, UpdateProduct product, Model model) {
        productService.updateProduct(id, product);
        return "redirect:/products/";
    }

    @GetMapping("/edit/{id}")
    String updateProduct(@PathVariable String id, Model model) {
        model.addAttribute("product",productService.getProduct(id));
        return "edit_product";
    }

}

