package com.github.starnowski.mybatis.hsqldb.model;

import java.util.Objects;

public class ProductWithManyToOneProductRelation {
    private Long productId;
    private Long relationId;

    public ProductWithManyToOneProductRelation(Long productId, Long relationId) {
        this.productId = productId;
        this.relationId = relationId;
    }

    public ProductWithManyToOneProductRelation() {
    }

    @Override
    public String toString() {
        return "ProductWithManyToOneProductRelation{" +
                "productId=" + productId +
                ", relationId=" + relationId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductWithManyToOneProductRelation that = (ProductWithManyToOneProductRelation) o;

        if (!Objects.equals(productId, that.productId)) return false;
        return Objects.equals(relationId, that.relationId);
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (relationId != null ? relationId.hashCode() : 0);
        return result;
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
