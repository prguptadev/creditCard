package com.bank.agent.credit_card_service.repository;
import com.bank.agent.credit_card_service.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    List<CreditCard> findByCustomerId(String customerId);
    Optional<CreditCard> findByCardNumber(String cardNumber);
}