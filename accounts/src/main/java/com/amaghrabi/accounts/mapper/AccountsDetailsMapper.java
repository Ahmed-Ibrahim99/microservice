package com.amaghrabi.accounts.mapper;

import com.amaghrabi.accounts.dto.AccountDetailsDto;
import com.amaghrabi.accounts.model.Accounts;
import com.amaghrabi.accounts.model.Customer;

public class AccountsDetailsMapper {

    public static AccountDetailsDto mapToAccountDetailsDto(Accounts account,
                                                           Customer customer,
                                                           AccountDetailsDto accountDetailsDto) {
        accountDetailsDto.setName(customer.getName());
        accountDetailsDto.setEmail(customer.getEmail());
        accountDetailsDto.setMobileNumber(customer.getMobileNumber());
        accountDetailsDto.setAccountNumber(account.getAccountNumber());
        accountDetailsDto.setBranchAddress(account.getBranchAddress());
        accountDetailsDto.setAccountType(account.getAccountType());
        return accountDetailsDto;
    }

    public static Accounts mapToAccounts(AccountDetailsDto accountDetailsDto, Accounts accounts) {
        accounts.setAccountType(accountDetailsDto.getAccountType());
        accounts.setBranchAddress(accountDetailsDto.getBranchAddress());
        accounts.setAccountNumber(accountDetailsDto.getAccountNumber());

        return accounts;
    }

    public static Customer mapToCustomer(AccountDetailsDto accountDetailsDto, Customer customer) {
        customer.setName(accountDetailsDto.getName());
        customer.setEmail(accountDetailsDto.getEmail());
        customer.setMobileNumber(accountDetailsDto.getMobileNumber());

        return customer;
    }
}
