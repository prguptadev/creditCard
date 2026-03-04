package com.bank.agent.credit_card_service.service;

import com.bank.agent.credit_card_service.dto.CreditCardPaymentRequest;
import com.bank.agent.credit_card_service.model.CreditCard;

import java.util.List;

public interface CreditCardService {
    List<CreditCard> getCardsByCustomerId(String customerId);
    CreditCard payBill(CreditCardPaymentRequest request);
}
