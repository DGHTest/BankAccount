package com.banktest.account.persistence.mapper;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.persistence.entity.AccountEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDomain toAccountDomain(AccountEntity accountEntity);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "transactionEntities", ignore = true)
    })
    AccountEntity toAccountEntity(AccountDomain accountDomain);
}
