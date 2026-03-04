package com.bank.agent.credit_card_service.service;

import com.bank.agent.credit_card_service.dto.CreditCardPaymentRequest;
import com.bank.agent.credit_card_service.model.Account;
import com.bank.agent.credit_card_service.model.CreditCard;
import com.bank.agent.credit_card_service.repository.AccountRepository;
import com.bank.agent.credit_card_service.repository.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ConditionalOnProperty(name = "service.credit-card.use-mock", havingValue = "false", matchIfMissing = true)
public class DatabaseCreditCardService implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<CreditCard> getCardsByCustomerId(String customerId) {
        return creditCardRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public CreditCard payBill(CreditCardPaymentRequest request) {
        // 1. Validate source account
        Account sourceAccount = accountRepository.findByAccountNumber(request.getSourceAccountNumber())
            .orElseThrow(() -> new EntityNotFoundException("Source account not found."));
        
        if (!"ACTIVE".equals(sourceAccount.getStatus())) {
            throw new IllegalStateException("Source account is not active.");
        }
        if (sourceAccount.getBalance().compareTo(request.getPaymentAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds in source account.");
        }

        // 2. Validate credit card
        CreditCard creditCard = creditCardRepository.findByCardNumber(request.getCreditCardNumber())
            .orElseThrow(() -> new EntityNotFoundException("Credit card not found."));

        // 3. Perform debit from source account
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getPaymentAmount()));
        accountRepository.save(sourceAccount);

        // 4. Perform credit to credit card account
        creditCard.setTotalDue(creditCard.getTotalDue().subtract(request.getPaymentAmount()));
        // Logic to recalculate minDue can be added here if necessary
        
        return creditCardRepository.save(creditCard);
    }
}
