package com.mirim.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;
    private final FakeProductDb db;

    public ProductController(ProductService productService, FakeProductDb db) {
        this.productService = productService;
        this.db = db;
    }

    @GetMapping("/product")
    public Product product(@RequestParam Long id) {
        return productService.find(id);
    }

    @GetMapping("/db-count")
    public int dbCount() {
        return db.getCallCount();
    }
}
