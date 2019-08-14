package com.constants;

public enum FileHeader {
    CLIENT_INFORMATION("Client_Information"),
    PRODUCT_INFORMATION("Product_Information"),
    TOTAL_TRANSACTION_AMOUNT("Total_Transaction_Amount");


    private String header;

    FileHeader(String header) {
        this.header = header;
    }

    public String getHeaderName() {
        return header;
    }
}
