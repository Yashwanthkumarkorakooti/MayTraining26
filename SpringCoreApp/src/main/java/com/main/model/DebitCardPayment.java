package com.main.model;

import org.springframework.stereotype.Component;

@Component
public class DebitCardPayment implements Payment{
    @Override
    public String process() {
        return "Debit Card payment Processed";
    }
}
