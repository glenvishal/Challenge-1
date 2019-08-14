package com.constants;

public enum FieldLength {
    CLIENT_INFORMATION_START(3),
    CLIENT_INFORMATION_END(19),
    PRODUCT_INFORMATION_START(25),
    PRODUCT_INFORMATION_END(45),
    QUANTITY_LONG_START(52),
    QUANTITY_LONG_END(62),
    QUANTITY_SHORT_START(63),
    QUANTITY_SHORT_END(73);

    private int value;

    FieldLength(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
