package com.example.ajio.model;

public class ProductModel {

    private String url;
    private String seller;
    private String productName;
    private Long price;

    public ProductModel() {
    }

    public ProductModel(String url, String seller, String productName, Long price) {
        this.url = url;
        this.seller = seller;
        this.productName = productName;
        this.price = price;
    }

    public String getUrl() {
        return url;
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
