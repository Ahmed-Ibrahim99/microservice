package com.amaghrabi.accounts.repository;

import com.amaghrabi.accounts.model.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    boolean existsByAccountNumber(Long accountNumber);

    Optional<Accounts> findByCustomerId(Long customerId);

    Optional<Accounts> findByAccountNumber(Long accountNumber);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
