package com.sysco.sampleService.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public abstract class BaseProductResponse {
    private String productId;
    private String productName;

    protected BaseProductResponse(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
