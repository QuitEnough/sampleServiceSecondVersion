package com.sysco.sampleService.controller;

import com.sysco.sampleService.model.ErrorResponse;
import com.sysco.sampleService.model.ProductEnvelopeResponse;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import com.sysco.sampleService.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private ProductService productService = mock(ProductService.class);
    private ProductController productController = new ProductController(productService);

    @Test
    public void testGetResponseBasedOnCalories_success() {
        ProductResponseBasedOnCalories productResponseBasedOnCalories =
                new ProductResponseBasedOnCalories("1", "product1");
        List<ProductResponseBasedOnCalories> list = new ArrayList<>();
        list.add(productResponseBasedOnCalories);

        ProductEnvelopeResponse<ProductResponseBasedOnCalories> productResponse =
                ProductEnvelopeResponse.<ProductResponseBasedOnCalories>builder()
                        .data(list)
                        .errorResponseList(List.of())
                        .build();

        when(productService.getProductEnvelopeResponseBasedOnCalories("100")).thenReturn(productResponse);
        ProductEnvelopeResponse<ProductResponseBasedOnCalories> response = productController.getResponseBasedOnCalories("100");

        assertNotNull(response);

        assertEquals("1", response.getData().get(0).getProductId());
        assertEquals("product1", response.getData().get(0).getProductName());

        assertTrue(response.getErrorResponseList().isEmpty());

    }

    @Test
    public void testGetResponseBasedOnQuantity_success() {
        ProductResponseBasedOnQuantity productResponseBasedOnQuantity =
                new ProductResponseBasedOnQuantity("1", "product1", "3");
        List<ProductResponseBasedOnQuantity> list = new ArrayList<>();
        list.add(productResponseBasedOnQuantity);

        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> productResponse =
                ProductEnvelopeResponse.<ProductResponseBasedOnQuantity>builder()
                        .data(list)
                        .errorResponseList(List.of())
                        .build();

        when(productService.getProductEnvelopeResponseBasedOnQuantity("3")).thenReturn(productResponse);
        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> response = productController.getResponseBasedOnQuantity("3");

        assertNotNull(response);

        assertEquals("1", response.getData().get(0).getProductId());
        assertEquals("product1", response.getData().get(0).getProductName());
        assertEquals("3", response.getData().get(0).getQuantityOnHand());

        assertTrue(response.getErrorResponseList().isEmpty());
    }



    @Test
    public void testGetResponseBasedOnQuantity_fail() {
        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> productResponse =
                ProductEnvelopeResponse.<ProductResponseBasedOnQuantity>builder()
                        .data(null)
                        .errorResponseList(List.of(new ErrorResponse(404, "Product not found")))
                        .build();
        when(productService.getProductEnvelopeResponseBasedOnQuantity("3")).thenReturn(productResponse);
        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> response = productController.getResponseBasedOnQuantity("3");

        assertNotNull(response);

        assertEquals(404, response.getErrorResponseList().get(0).getStatusCode());
        assertEquals("Product not found", response.getErrorResponseList().get(0).getMessage());

        assertNull(response.getData());
    }

    @Test
    public void testGetResponseBasedOnCalories_fail() {
        ProductEnvelopeResponse<ProductResponseBasedOnCalories> productResponse =
                ProductEnvelopeResponse.<ProductResponseBasedOnCalories>builder()
                        .data(null)
                        .errorResponseList(List.of(new ErrorResponse(404, "Product not found")))
                        .build();
        when(productService.getProductEnvelopeResponseBasedOnCalories("100")).thenReturn(productResponse);
        ProductEnvelopeResponse<ProductResponseBasedOnCalories> response = productController.getResponseBasedOnCalories("100");

        assertNotNull(response);

        assertEquals(404, response.getErrorResponseList().get(0).getStatusCode());
        assertEquals("Product not found", response.getErrorResponseList().get(0).getMessage());

        assertNull(response.getData());
    }



}
