package com.zigzag.auction.dto.response;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private List<ProductResponseDto> products;
    private List<LotResponseDto> lots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public List<LotResponseDto> getLots() {
        return lots;
    }

    public void setLots(List<LotResponseDto> lots) {
        this.lots = lots;
    }
}
