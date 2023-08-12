package com.github.starnowski.mybatis.h2.requests;

import java.util.List;
import java.util.UUID;

public class ListProducts {

    private List<Integer> productIds;
    private List<String> uuids;

    public List<String> getUuids() {
        return uuids;
    }

    public ListProducts withUuids(List<String> uuids) {
        this.uuids = uuids;
        return this;
    }

    public ListProducts withProductIds(List<Integer> productIds) {
        this.productIds = productIds;
        return this;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

}
