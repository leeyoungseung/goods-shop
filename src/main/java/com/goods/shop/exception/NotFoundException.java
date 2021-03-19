package com.goods.shop.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Can't find Resource.");
    }
}
