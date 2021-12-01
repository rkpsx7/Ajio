package com.pns.ajio.model;

import java.util.ArrayList;

public class ProductModel {

    private String url;
    private String key;
    private ArrayList<String> ordered;
    private ArrayList<String> wishlisted;
    private String seller;
    private String productName;
    private Long price;

    public ProductModel() {
    }

    public ProductModel(String url, String key, ArrayList<String> ordered, ArrayList<String> wishlisted, String seller, String productName, Long price) {
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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getOrdered() {
        return ordered;
    }

    public void setOrdered(ArrayList<String> ordered) {
        this.ordered = ordered;
    }

    public ArrayList<String> getWishlisted() {
        return wishlisted;
    }

    public void setWishlisted(ArrayList<String> wishlisted) {
        this.wishlisted = wishlisted;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}