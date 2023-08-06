package com.github.starnowski.mybatis.h2.mappers;

import com.github.starnowski.mybatis.h2.configuration.PersistenceAutoConfig;
import com.github.starnowski.mybatis.h2.model.Product;
import com.github.starnowski.mybatis.h2.requests.ListProducts;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = PersistenceAutoConfig.class)
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    SqlSessionFactory sessionFactory;

    @Test
    public void whenRecordsInDatabase_shouldReturnProducts() {
        // WHEN
        List<Product> products = productMapper.getProducts(new ListProducts().withProductIds(Arrays.asList(1, 2, 3)));

        // THEN
        assertThat(products).isNotNull().hasSize(3);
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnPreparedStatementWithCorrectNumberOfParameters() {
        // GIVEN
        Configuration configuration = sessionFactory.getConfiguration();

        Map pars = new HashMap<String, Object>();
        pars.put("name", "john");
        pars.put("number", 1345);

        MappedStatement ms = configuration.getMappedStatement("com.github.starnowski.mybatis.h2.mappers.ProductMapper.getProducts");
        // WHEN
                BoundSql boundSql = ms.getBoundSql(new ListProducts().withProductIds(Arrays.asList(1, 2, 3)));
        String sql = boundSql.getSql();

        // THEN
        boundSql.getParameterMappings();
        assertThat(boundSql.getParameterMappings()).isNotNull().hasSize(3);
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnProductsByUUIDs() {
        // WHEN
        List<Product> products = productMapper.getProductsByUuid(new ListProducts().withUuids(Arrays.asList("04964ddf-db22-4612-b142-49da2bd9ff0b", "f7a66adf-e656-4bb5-b192-7a62f3be8aea")));

        // THEN
        assertThat(products).isNotNull().hasSize(2);
    }
}