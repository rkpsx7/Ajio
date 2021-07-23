package com.example.ajio.model;

public class ProductModel {

    private String url;
    private String key;
    private boolean ordered;
    private boolean wishlisted;
    private String seller;
    private String productName;
    private Long price;

    public ProductModel() {
    }

    public ProductModel(String url, String key, boolean ordered, boolean wishlisted, String seller, String productName, Long price) {
        this.url = url;
        this.key = key;
        this.ordered = ordered;
        this.wishlisted = wishlisted;
        this.seller = seller;
        this.productName = productName;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public boolean isWishlisted() {
        return wishlisted;
    }

    public String getSeller() {
        return seller;
    }

    public String getProductName() {
        return productName;
    }

    public Long getPrice() {
        return price;
    }
}
