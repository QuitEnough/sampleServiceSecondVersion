package com.sysco.sampleService.model;

import lombok.Getter;

@Getter
public class ProductResponseBasedOnQuantity extends BaseProductResponse {

    private String quantityOnHand;

    public ProductResponseBasedOnQuantity(String productId, String productName, String quantityOnHand) {
        super(productId, productName);
        this.quantityOnHand = quantityOnHand;
    }
}
