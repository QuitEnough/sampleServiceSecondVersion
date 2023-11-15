package com.sysco.sampleService.dao;

import com.sysco.sampleService.exception.RowMapperException;
import com.sysco.sampleService.exception.UnprocessableEntityException;
import com.sysco.sampleService.model.ProductResponseBasedOnCalories;
import com.sysco.sampleService.model.ProductResponseBasedOnQuantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ProductDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<ProductResponseBasedOnCalories> getProductResponseBasedOnCalories(String calories) {
        log.info("[Dao] executing query to get product data for calories {}", calories);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("CALORIES", Integer.parseInt(calories)); // CALORIES, 50

        try{
            return namedParameterJdbcTemplate.query("""
                SELECT
                    products.details.product_id,
                    products.details.value ->> 'name' as product_name
                FROM
                    products.details
                INNER JOIN
                    products.nutrition
                ON
                    products.details.seller_id = products.nutrition.seller_id
                AND
                    products.details.product_id = products.nutrition.product_id
                WHERE
                    (products.nutrition.value ->> 'calories')::int > :CALORIES;
                """, mapSqlParameterSource, rowMapper);
        } catch (EmptyResultDataAccessException ex) {
            log.warn("[Dao] No product data found for calories {}", calories);
            return List.of();
        } catch (Exception ex) {
            log.error("[Dao] An error occurred when querying product data for calories {}", calories);
            throw new UnprocessableEntityException("Unable to query DB" + ex.getMessage(), ex);
        }

    }

    private RowMapper<ProductResponseBasedOnCalories> rowMapper = (rs, rowNum) -> {
        try {
            String productId = rs.getString("product_id");
            String productName = rs.getString("product_name");
            return new ProductResponseBasedOnCalories(productId, productName);
        } catch (Exception e) {
            log.error("[Dao] exception happened during parsing data ProductResponseBasedOnCalories");
            throw new RowMapperException("Error parsing DB context for ProductResponseBasedOnCalories", e);
        }
    };


    public List<ProductResponseBasedOnQuantity> getProductResponseBasedOnQuantity(String quantity) {
        log.info("[Dao] executing query to get product data for quantity {}", quantity);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("QUANTITY", Integer.parseInt(quantity));

        try {
            return namedParameterJdbcTemplate.query("""
                SELECT
                    products.details.product_id,
                    products.details.value ->> 'name' as product_name,
                    products.inventory.value ->> 'quantity_on_hand' as quantity_on_hand
                FROM
                    products.details
                INNER JOIN
                    products.inventory
                ON
                    products.details.seller_id = products.inventory.seller_id
                AND
                    products.details.product_id = products.inventory.product_id
                WHERE
                    (products.inventory.value ->> 'quantity_on_hand')::int >= :QUANTITY;
                """, mapSqlParameterSource, rowMapperForQuantity);
        } catch (EmptyResultDataAccessException ex) {
            log.warn("[Dao] No product data found for quantity {}", quantity);
            return List.of();
        } catch (Exception ex) {
            log.error("[Dao] An error occurred when querying product data for quantity {}", quantity);
            throw new UnprocessableEntityException("Unable to query DB" + ex.getMessage(), ex);
        }

    }

    private RowMapper<ProductResponseBasedOnQuantity> rowMapperForQuantity = (rs, rowNum) -> {
        try {
            String productId = rs.getString("product_id");
            String productName = rs.getString("product_name");
            String quantityOnHand = rs.getString("quantity_on_hand");
            return new ProductResponseBasedOnQuantity(productId, productName, quantityOnHand);
        } catch (Exception e) {
            log.error("[Dao] exception happened during parsing data ProductResponseBasedOnQuantity");
            throw new RowMapperException("Error parsing DB context for ProductResponseBasedOnQuantity", e);
        }

    };


}
