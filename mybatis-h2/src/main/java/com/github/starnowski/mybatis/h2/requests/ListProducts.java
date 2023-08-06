package com.github.starnowski.mybatis.h2.requests;

import java.util.List;

public class ListProducts {

    private List<Integer> productIds;

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
