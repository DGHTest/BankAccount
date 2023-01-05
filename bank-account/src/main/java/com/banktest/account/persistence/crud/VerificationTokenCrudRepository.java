package com.banktest.account.persistence.crud;

import com.banktest.account.persistence.entity.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface VerificationTokenCrudRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE VerificationToken AS vt SET vt.token = :newToken WHERE vt.token = :oldToken")
    void updateTokenById(@Param("newToken") String newToken, @Param("oldToken") String oldToken);
}
