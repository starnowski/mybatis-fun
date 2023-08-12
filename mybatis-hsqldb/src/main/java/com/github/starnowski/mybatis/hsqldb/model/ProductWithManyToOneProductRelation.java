package com.github.starnowski.mybatis.hsqldb.model;

public class ProductWithManyToOneProductRelation {
    private Long productId;
    private Long relationId;

    public ProductWithManyToOneProductRelation(Long productId, Long relationId) {
        this.productId = productId;
        this.relationId = relationId;
    }

    public ProductWithManyToOneProductRelation() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
