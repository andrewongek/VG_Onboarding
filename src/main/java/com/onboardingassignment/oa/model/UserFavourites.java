package com.onboardingassignment.oa.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "user_favourites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"})
)
public class UserFavourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
