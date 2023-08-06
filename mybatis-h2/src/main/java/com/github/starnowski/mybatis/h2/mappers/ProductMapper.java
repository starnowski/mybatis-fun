package com.github.starnowski.mybatis.h2.mappers;

import com.github.starnowski.mybatis.h2.model.Product;
import com.github.starnowski.mybatis.h2.requests.ListProducts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface ProductMapper {

    List<Product> getProducts(ListProducts listProducts);
    List<Product> getProductsByUuid(ListProducts listProducts);
}
