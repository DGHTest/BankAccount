package com.banktest.account.persistence.mapper;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.persistence.entity.TransactionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDomain toTransactionDomain(TransactionEntity transactionEntity);

    List<TransactionDomain> toTransactionDomains(List<TransactionEntity> transactionEntities);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "transactionType", ignore = true),
            @Mapping(target = "accountEntity", ignore = true)
    })
    TransactionEntity toTransactionEntity(TransactionDomain transactionDomain);
}
