package com.github.starnowski.mybatis.h2.requests;

import java.util.List;

public class ListProducts {

    private List<Integer> productIds;

    public ListProducts withProductIds(List<Integer> productIds) {
        this.productIds = productIds;
        return this;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

}
