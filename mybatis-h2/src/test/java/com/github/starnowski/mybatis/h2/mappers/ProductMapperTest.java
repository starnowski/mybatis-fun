package com.github.starnowski.mybatis.h2.mappers;

import com.github.starnowski.mybatis.h2.configuration.PersistenceAutoConfig;
import com.github.starnowski.mybatis.h2.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PersistenceAutoConfig.class)
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    public void whenRecordsInDatabase_shouldReturnProducts() {
        // WHEN
        List<Product> products = productMapper.getProducts(null);

        // THEN
        assertThat(products).isNotNull();
    }
}