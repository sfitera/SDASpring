package org.dreamteam.sda.controller.web;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.dreamteam.sda.controller.web.request.CreateProduct;
import org.dreamteam.sda.controller.web.request.UpdateProduct;
import org.dreamteam.sda.model.Product;
import org.dreamteam.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



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
        model.addAttribute("createProduct", new Product());
        return "products";
    }

    @PostMapping("/add")
    String addProduct(@Valid CreateProduct product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("products",productService.getProducts());
            model.addAttribute("createProduct", product);
            return "products";
        }
        productService.addProduct(product.getName(),product.getPrice());
        return "redirect:/products/";
    }

    @GetMapping("/delete/{id}")
    String deleteProduct(@PathVariable String id, Model model) {
        productService.deleteProduct(id);
        return "redirect:/products/";
    }

    @PostMapping("/update/{id}")
    String updateProduct(@PathVariable String id, @Valid UpdateProduct product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("updateProduct", product);
            return "edit_product";
        }
        productService.updateProduct(product.getId(), new Product(product.getId(), product.getName(), product.getPrice()));
        return "redirect:/products/";
    }

    @GetMapping("/edit/{id}")
    String updateProduct(@PathVariable String id, Model model) {
        model.addAttribute("updateProduct",productService.getProduct(id));
        return "edit_product";
    }

}

