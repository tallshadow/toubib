package com.adouae.toubib.impl.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adouae.toubib.impl.persistence.entity.PasswordResetToken;


@Repository
public interface  PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	public PasswordResetToken findByToken(String token);

//    PasswordResetToken findByUser(User user);
//
//    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
//
//    void deleteByExpiryDateLessThan(Date now);
//
//    @Modifying
//    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
}

