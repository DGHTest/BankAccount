package com.banktest.account.persistence.entity;

import com.banktest.account.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "account_name", length=50, nullable=false)
    private String accountName;

    @Column(length=100, unique=true, nullable=false)
    private String email;

    @Column(length=60, nullable=false)
    private String password;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private Boolean enabled = false;

    @OneToMany(mappedBy = "accountEntity")
    private List<TransactionEntity> transactionEntities;
}
