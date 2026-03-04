package com.bank.agent.credit_card_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String status;
}
