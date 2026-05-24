package com.main.service;

import com.main.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
    public String processPaymentForCustomer(Payment payment){
        return payment.process();
    }
}
