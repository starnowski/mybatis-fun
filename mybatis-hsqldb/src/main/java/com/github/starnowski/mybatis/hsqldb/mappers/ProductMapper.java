package com.github.starnowski.mybatis.hsqldb.mappers;

import com.github.starnowski.mybatis.hsqldb.model.Product;
import com.github.starnowski.mybatis.hsqldb.model.ProductWithManyToOneProductRelation;
import com.github.starnowski.mybatis.hsqldb.requests.ListProducts;

import java.util.List;

public interface ProductMapper {

    List<Product> getProducts(ListProducts listProducts);
    List<Product> getProductsByUuid(ListProducts listProducts);
    List<Product> getProductsByUuidWithEscapingUtils(ListProducts listProducts);

    List<ProductWithManyToOneProductRelation> getOuterJoin();

}
