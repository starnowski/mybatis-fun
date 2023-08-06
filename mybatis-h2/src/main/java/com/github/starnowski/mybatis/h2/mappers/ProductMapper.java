package com.github.starnowski.mybatis.h2.mappers;

import com.github.starnowski.mybatis.h2.model.Product;
import com.github.starnowski.mybatis.h2.requests.ListProducts;

import java.util.List;

public interface ProductMapper {

    List<Product> getProducts(ListProducts listProducts);
}
