package com.model;

public class FileModel {
    private String clientInformation;
    private String productInformation;
    private String quantityLong;
    private String quantityShort;
    public String getClientInformation() {
        return clientInformation;
    }
    public void setClientInformation(String clientInformation) {
        this.clientInformation = clientInformation;
    }
    public String getProductInformation() {
        return productInformation;
    }
    public void setProductInformation(String productInformation) {
        this.productInformation = productInformation;
    }
    public String getQuantityLong() {
        return quantityLong;
    }
    public void setQuantityLong(String quantityLong) {
        this.quantityLong = quantityLong;
    }
    public String getQuantityShort() {
        return quantityShort;
    }
    public void setQuantityShort(String quantityShort) {
        this.quantityShort = quantityShort;
    }
    @Override
    public String toString() {
        return "FileModel{" +
                "clientInformation='" + clientInformation + '\'' +
                ", productInformation='" + productInformation + '\'' +
                ", quantityLong='" + quantityLong + '\'' +
                ", quantityShort='" + quantityShort + '\'' +
                '}';
    }
}
