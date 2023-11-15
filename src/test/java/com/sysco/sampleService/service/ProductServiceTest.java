package com.sysco.sampleService.service;

import com.sysco.sampleService.dao.ProductDao;
import com.sysco.sampleService.model.ProductEnvelopeResponse;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private ProductDao productDao = Mockito.mock(ProductDao.class);
    private ProductService productService = new ProductService(productDao);

    @Test
    public void testGetResponseBasedOnCalories_success() {
        ProductResponseBasedOnCalories productResponseBasedOnCalories =
                new ProductResponseBasedOnCalories("1", "product1");
        when(productDao.getProductResponseBasedOnCalories("100"))
                .thenReturn(List.of(productResponseBasedOnCalories));

        ProductEnvelopeResponse<ProductResponseBasedOnCalories> response =
                productService.getProductEnvelopeResponseBasedOnCalories("100");

        assertNotNull(productResponseBasedOnCalories);

        assertEquals("1", response.getData().get(0).getProductId());
        assertEquals("product1", response.getData().get(0).getProductName());

        assertTrue(response.getErrorResponseList().isEmpty());
    }

    @Test
    public void testGetResponseBasedOnCalories_fail() {
        when(productDao.getProductResponseBasedOnCalories("100"))
                .thenReturn(List.of());

        ProductEnvelopeResponse<ProductResponseBasedOnCalories> response =
                productService.getProductEnvelopeResponseBasedOnCalories("100");

        assertEquals(404, response.getErrorResponseList().get(0).getStatusCode());
        assertEquals("Product not found for <100>", response.getErrorResponseList().get(0).getMessage());

        assertTrue(response.getData().isEmpty());
    }

    @Test
    public void testGetResponseBasedOnQuantity_success() {
        ProductResponseBasedOnQuantity productResponseBasedOnQuantity =
                new ProductResponseBasedOnQuantity("1", "product1", "3");
        when(productDao.getProductResponseBasedOnQuantity("3"))
                .thenReturn(List.of(productResponseBasedOnQuantity));

        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> response =
                productService.getProductEnvelopeResponseBasedOnQuantity("3");

        assertNotNull(productResponseBasedOnQuantity);

        assertEquals("1", response.getData().get(0).getProductId());
        assertEquals("product1", response.getData().get(0).getProductName());
        assertEquals("3", response.getData().get(0).getQuantityOnHand());

        assertTrue(response.getErrorResponseList().isEmpty());
    }

    @Test
    public void testGetResponseBasedOnQuantity_fail() {
        when(productDao.getProductResponseBasedOnQuantity("3"))
                .thenReturn(List.of());

        ProductEnvelopeResponse<ProductResponseBasedOnQuantity> response =
                productService.getProductEnvelopeResponseBasedOnQuantity("3");

        assertEquals(404, response.getErrorResponseList().get(0).getStatusCode());
        assertEquals("Product not found for <3>", response.getErrorResponseList().get(0).getMessage());

        assertTrue(response.getData().isEmpty());
    }
}
