package com.neriidev.pix.infrastructure.out.persistence.entity;


import com.neriidev.pix.domain.model.Ledger;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger")
public class LedgerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ledger.LedgerEntryType type;

    private String transactionId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
