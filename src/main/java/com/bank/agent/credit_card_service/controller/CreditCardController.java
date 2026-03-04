package com.bank.agent.credit_card_service.controller;

import com.bank.agent.credit_card_service.dto.CreditCardPaymentRequest;
import com.bank.agent.credit_card_service.model.CreditCard;
import com.bank.agent.credit_card_service.service.CreditCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin; 


@RestController
@RequestMapping("/v2")
@CrossOrigin(origins = "http://localhost:3001") 
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    // Get all credit cards for a specific customer
    @GetMapping("/customers/{customerId}/credit-cards")
    public ResponseEntity<List<CreditCard>> getCustomerCreditCards(@PathVariable String customerId) {
        List<CreditCard> cards = creditCardService.getCardsByCustomerId(customerId);
        return ResponseEntity.ok(cards);
    }

    // Pay a credit card bill
    @PostMapping("/transactions/credit-card-payment")
    public ResponseEntity<CreditCard> payCreditCardBill(@Valid @RequestBody CreditCardPaymentRequest request) {
        CreditCard updatedCard = creditCardService.payBill(request);
        return ResponseEntity.ok(updatedCard);
    }
}
