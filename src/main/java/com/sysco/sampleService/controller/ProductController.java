package com.sysco.sampleService.controller;

import com.sysco.sampleService.model.ProductEnvelopeResponse;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import com.sysco.sampleService.service.ProductService;
import com.sysco.sampleService.validation.InputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product/{calories}/calories", produces = "application/json")
    public ProductEnvelopeResponse<ProductResponseBasedOnCalories> getResponseBasedOnCalories(@PathVariable @InputValidator String calories){
        log.info("[RequestParams] get product data for calories {}", calories);
        ProductEnvelopeResponse<ProductResponseBasedOnCalories> response = productService.getProductEnvelopeResponseBasedOnCalories(calories);
        log.info("[Response] get product data {}", response);
        return response;
    }

    @GetMapping(value = "/product/{quantity}/quantity", produces = "application/json")
    public ProductEnvelopeResponse<ProductResponseBasedOnQuantity> getResponseBasedOnQuantity(@PathVariable @InputValidator String quantity){
        log.info("[RequestParams] get product data for quantity {}", quantity);
        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> response = productService.getProductEnvelopeResponseBasedOnQuantity(quantity);
        log.info("[Response] get product data {}", response);
        return response;
    }

}
