package com.bank.agent.credit_card_service.service;

import com.bank.agent.credit_card_service.dto.CreditCardPaymentRequest;
import com.bank.agent.credit_card_service.model.CreditCard;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "service.credit-card.use-mock", havingValue = "true")
public class MockCreditCardService implements CreditCardService {

    private final List<CreditCard> mockCards = new ArrayList<>();

    public MockCreditCardService() {
        // Populate with "plenty of data"
        for (int i = 1; i <= 20; i++) {
            CreditCard card = new CreditCard();
            card.setId("MOCK-CC-" + i);
            card.setCustomerId("CUST-" + String.format("%04d", (i % 5) + 1));
            card.setCardNumber("1111-2222-3333-" + String.format("%04d", i));
            card.setCardName("Mock Card " + i);
            card.setTotalDue(new BigDecimal(100 * i));
            card.setMinDue(new BigDecimal(10 * i));
            card.setDueDate(LocalDate.now().plusDays(i));
            mockCards.add(card);
        }
    }

    @Override
    public List<CreditCard> getCardsByCustomerId(String customerId) {
        return mockCards.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public CreditCard payBill(CreditCardPaymentRequest request) {
        CreditCard card = mockCards.stream()
                .filter(c -> c.getCardNumber().equals(request.getCreditCardNumber()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Mock credit card not found."));

        card.setTotalDue(card.getTotalDue().subtract(request.getPaymentAmount()));
        return card;
    }
}
