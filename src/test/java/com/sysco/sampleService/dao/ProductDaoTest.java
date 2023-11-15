package com.sysco.sampleService.dao;

import com.sysco.sampleService.exception.UnprocessableEntityException;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductDaoTest {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
    private final ProductDao productDao = new ProductDao(namedParameterJdbcTemplate);

    @Test
    public void testGetProductResponseBasedOnCalories_success() {
        ProductResponseBasedOnCalories productResponse =
                new ProductResponseBasedOnCalories("1", "product1");
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenReturn(List.of(productResponse));
        List<ProductResponseBasedOnCalories> responseList = productDao.getProductResponseBasedOnCalories("100");
        assertNotNull(responseList);
        assertEquals("1", responseList.get(0).getProductId());
        assertEquals("product1", responseList.get(0).getProductName());
        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class));
    }

    @Test
    public void testGetProductResponseBasedOnQuantity_success() {
        ProductResponseBasedOnQuantity productResponse =
                new ProductResponseBasedOnQuantity("1", "product1", "5");
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenReturn(List.of(productResponse));
        List<ProductResponseBasedOnQuantity> responseList = productDao.getProductResponseBasedOnQuantity("5");

        assertNotNull(responseList);
        assertEquals("1", responseList.get(0).getProductId());
        assertEquals("product1", responseList.get(0).getProductName());
        assertEquals("5", responseList.get(0).getQuantityOnHand());
        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class));
    }

    @Test
    public void testGetProductResponseBasedOnQuantity_fail() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenThrow(EmptyResultDataAccessException.class);

        List<ProductResponseBasedOnQuantity> responseList = productDao.getProductResponseBasedOnQuantity("5");

        assertTrue(responseList.isEmpty());
    }

    @Test
    public void testGetProductResponseBasedOnCalories_fail() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenThrow(EmptyResultDataAccessException.class);

        List<ProductResponseBasedOnCalories> responseList = productDao.getProductResponseBasedOnCalories("100");

        assertTrue(responseList.isEmpty());
    }

    @Test
    public void testGetProductResponseBasedOnCalories_fail2() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenThrow(UnprocessableEntityException.class);
        try {
            productDao.getProductResponseBasedOnCalories("50");
        } catch (UnprocessableEntityException e) {
            assertTrue(e.getMessage().contains("Unable to query DB"));
        }
    }

    @Test
    public void testGetProductResponseBasedOnQuantity_fail2() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
                any(RowMapper.class))).thenThrow(UnprocessableEntityException.class);
        try {
            productDao.getProductResponseBasedOnQuantity("3");
        } catch (UnprocessableEntityException e) {
            assertTrue(e.getMessage().contains("Unable to query DB"));
        }
    }


}
