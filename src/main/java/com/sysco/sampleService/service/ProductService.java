package com.sysco.sampleService.service;

import com.sysco.sampleService.dao.ProductDao;
import com.sysco.sampleService.model.ErrorResponse;
import com.sysco.sampleService.model.ProductEnvelopeResponse;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductEnvelopeResponse<ProductResponseBasedOnCalories> getProductEnvelopeResponseBasedOnCalories(String calories) {

        List<ProductResponseBasedOnCalories> productResponseBasedOnCalories = productDao.getProductResponseBasedOnCalories(calories);

        if (productResponseBasedOnCalories.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), format("Product not found for <%s>", calories));
            return ProductEnvelopeResponse.generateResponse(List.of(), List.of(errorResponse));
        }
        log.info("[Service] get product data for: {}", productResponseBasedOnCalories);
        return ProductEnvelopeResponse.generateResponse(productResponseBasedOnCalories, List.of());
    }

    public ProductEnvelopeResponse<ProductResponseBasedOnQuantity> getProductEnvelopeResponseBasedOnQuantity(String quantity) {

        List<ProductResponseBasedOnQuantity> productResponseBasedOnQuantities = productDao.getProductResponseBasedOnQuantity(quantity);

        if (productResponseBasedOnQuantities.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), format("Product not found for <%s>", quantity));
            return ProductEnvelopeResponse.generateResponse(List.of(), List.of(errorResponse));
        }
        log.info("[Service] get product data for: {}", productResponseBasedOnQuantities);
        return ProductEnvelopeResponse.generateResponse(productResponseBasedOnQuantities, List.of());
    }
}
