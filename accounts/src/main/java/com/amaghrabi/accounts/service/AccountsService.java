package com.amaghrabi.accounts.service;

import com.amaghrabi.accounts.dto.AccountDetailsDto;
import com.amaghrabi.accounts.dto.AccountsDto;
import com.amaghrabi.accounts.exception.ResourceNotFoundException;
import com.amaghrabi.accounts.mapper.AccountsDetailsMapper;
import com.amaghrabi.accounts.mapper.AccountsMapper;
import com.amaghrabi.accounts.model.Accounts;
import com.amaghrabi.accounts.model.Customer;
import com.amaghrabi.accounts.repository.AccountsRepository;
import com.amaghrabi.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    public void createAccount(AccountsDto accountsDto) {
        try {
            if (accountsDto == null) {
                throw new IllegalArgumentException("AccountsDto cannot be null");
            }
            boolean customerExists = customerRepository.existsByCustomerId(accountsDto.getCustomerId());
            if (customerExists) {
                Accounts account = AccountsMapper.mapToAccounts(accountsDto, new Accounts());
                long randomAccNumber;
                do {
                    randomAccNumber = 1000000000L + new Random().nextInt(900000000);
                } while (accountsRepository.existsByAccountNumber(randomAccNumber));
                account.setCustomerId(accountsDto.getCustomerId());
                account.setAccountNumber(randomAccNumber);
                accountsRepository.save(account);
            } else {
                throw new ResourceNotFoundException("Customer", "customerId", accountsDto.getCustomerId().toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account", e);
        }

    }

    public AccountDetailsDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customer.getCustomerId().toString())
        );

        return AccountsDetailsMapper.mapToAccountDetailsDto(account,
                customer,
                new AccountDetailsDto());
    }

    public boolean updateAccountDetails(AccountDetailsDto accountDetailsDto) {
        boolean isUpdated = false;
        if (accountDetailsDto != null) {
            Accounts account = accountsRepository.findByAccountNumber(accountDetailsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account",
                            "AccountNumber",
                            accountDetailsDto.getAccountNumber().toString())
            );

            Accounts updatedAccount = AccountsDetailsMapper.mapToAccounts(accountDetailsDto, account);
            accountsRepository.save(updatedAccount);

            Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer",
                            "CustomerId",
                            account.getCustomerId().toString())
            );

            Customer updatedCustomer = AccountsDetailsMapper.mapToCustomer(accountDetailsDto, customer);
            customerRepository.save(updatedCustomer);

            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteAccount(Long accountNumber) {
        boolean isDeleted = false;
        if (accountNumber != null) {
            accountsRepository.deleteById(accountNumber);
            isDeleted = true;
        }
        return isDeleted;
    }

}
