package com.game.microbook.catalog.web.controllers;

import com.game.microbook.catalog.domain.PagedResult;
import com.game.microbook.catalog.domain.Product;
import com.game.microbook.catalog.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.game.microbook.catalog.domain.ProductNotFoundException.productNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductById(@PathVariable String code) {
//        sleep();
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> productNotFoundException(code));
    }

    void sleep() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
