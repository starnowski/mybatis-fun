package com.github.starnowski.mybatis.hsqldb.mappers;

import com.github.starnowski.mybatis.hsqldb.model.Product;
import com.github.starnowski.mybatis.hsqldb.model.ProductWithManyToOneProductRelation;
import com.github.starnowski.mybatis.hsqldb.requests.ListProducts;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = PersistenceAutoConfig.class)
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    SqlSessionFactory sessionFactory;

    private static Stream<Arguments> provideUUIDsWithSQLInjectedStatementAndExpectedIds() {
        return Stream.of(
                Arguments.of(asList("04964ddf-db22-4612-b142-49da2bd9ff0b", "' --", "f7a66adf-e656-4bb5-b192-7a62f3be8aea"), asList(1L, 2L)),
                Arguments.of(asList("04964ddf-db22-4612-b142-49da2bd9ff0b", "' --", "980d6547-3a20-40f5-918f-e2f08df4b014"), asList(1L, 3L)),
                Arguments.of(asList("04964ddf-db22-4612-b142-49da2bd9ff0b", "980d6547-3a20-40f5-918f-e2f08df4b014", "f7a66adf-e656-4bb5-b192-7a62f3be8aea"), asList(1L, 2L, 3L)),
                Arguments.of(asList("' ' ' --", "' --", "f7a66adf-e656-4bb5-b192-7a62f3be8aea", "' ' --"), List.of(2L))
        );
    }

    private static ProductWithManyToOneProductRelation joinRelation(Long relationId, Long productId) {
        return new ProductWithManyToOneProductRelation(productId, relationId);
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnProducts() {
        // WHEN
        List<Product> products = productMapper.getProducts(new ListProducts().withProductIds(asList(1, 2, 3)));

        // THEN
        assertThat(products).isNotNull().hasSize(3);
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnPreparedStatementWithCorrectNumberOfParameters() {
        // GIVEN
        Configuration configuration = sessionFactory.getConfiguration();
        MappedStatement ms = configuration.getMappedStatement("com.github.starnowski.mybatis.hsqldb.mappers.ProductMapper.getProducts");

        // WHEN
        BoundSql boundSql = ms.getBoundSql(new ListProducts().withProductIds(asList(1, 2, 3)));
        String sql = boundSql.getSql();

        // THEN
        boundSql.getParameterMappings();
        assertThat(boundSql.getParameterMappings()).isNotNull().hasSize(3);
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnProductsByUUIDs() {
        // WHEN
        List<Product> products = productMapper.getProductsByUuid(new ListProducts().withUuids(asList("04964ddf-db22-4612-b142-49da2bd9ff0b", "f7a66adf-e656-4bb5-b192-7a62f3be8aea")));

        // THEN
        assertThat(products).isNotNull().hasSize(2);
    }

    @ParameterizedTest
    @MethodSource("provideUUIDsWithSQLInjectedStatementAndExpectedIds")
    public void whenRecordsInDatabase_shouldReturnProductsByUUIDsEventIfInputHasSQLInjectionAttack(List<String> uuids, List<Long> expectedIds) {
        // WHEN
        List<Product> products = productMapper.getProductsByUuid(new ListProducts().withUuids(uuids));

        // THEN
        assertThat(products).isNotNull().hasSize(expectedIds.size());
        assertThat(products.stream().map(product -> product.getId()).collect(Collectors.toSet())).isEqualTo(new HashSet<>(expectedIds));
    }

    @ParameterizedTest
    @MethodSource("provideUUIDsWithSQLInjectedStatementAndExpectedIds")
    public void whenRecordsInDatabase_shouldReturnProductsByUUIDsEventIfInputHasSQLInjectionAttackAndMethodUseCustomEscapeMethod(List<String> uuids, List<Long> expectedIds) {
        // WHEN
        List<Product> products = productMapper.getProductsByUuidWithEscapingUtils(new ListProducts().withUuids(uuids));

        // THEN
        assertThat(products).isNotNull().hasSize(expectedIds.size());
        assertThat(products.stream().map(product -> product.getId()).collect(Collectors.toSet())).isEqualTo(new HashSet<>(expectedIds));
    }

    @ParameterizedTest
    @MethodSource("provideUUIDsWithSQLInjectedStatementAndExpectedIds")
    public void whenRecordsInDatabase_shouldReturnPreparedStatementWithNofParametersForPreparedStatement(List<String> uuids, List<Long> expectedIds) {
        // GIVEN
        Configuration configuration = sessionFactory.getConfiguration();
        MappedStatement ms = configuration.getMappedStatement("com.github.starnowski.mybatis.hsqldb.mappers.ProductMapper.getProductsByUuidWithEscapingUtils");

        // WHEN
        BoundSql boundSql = ms.getBoundSql(new ListProducts().withUuids(uuids));

        // THEN
        boundSql.getParameterMappings();
        assertThat(boundSql.getParameterMappings()).isNotNull().isEmpty();
    }

    @Test
    public void shouldReturnOuterJoinResults() {
        // GIVEN
        Set<ProductWithManyToOneProductRelation> expectedResults = new HashSet<>(Arrays.asList(joinRelation(1L, 1L),
                joinRelation(2L, 1L),
                joinRelation(3L, 1L),
                joinRelation(4L, null),
                joinRelation(5L, null),
                joinRelation(6L, 3L),
                joinRelation(null, 2L)
        ));

        // WHEN
        List<ProductWithManyToOneProductRelation> results = productMapper.getOuterJoin();

        // THEN
        assertThat(new HashSet<>(results)).isNotNull().isEqualTo(expectedResults);
    }

    @Test
    public void shouldReturnInnerJoinResults() {
        // GIVEN
        Set<ProductWithManyToOneProductRelation> expectedResults = new HashSet<>(Arrays.asList(joinRelation(1L, 1L),
                joinRelation(2L, 1L),
                joinRelation(3L, 1L),
                joinRelation(6L, 3L)
        ));

        // WHEN
        List<ProductWithManyToOneProductRelation> results = productMapper.getInnerJoin();

        // THEN
        assertThat(new HashSet<>(results)).isNotNull().isEqualTo(expectedResults);
    }
}