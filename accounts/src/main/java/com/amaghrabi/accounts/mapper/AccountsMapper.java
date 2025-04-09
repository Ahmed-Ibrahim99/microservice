package com.amaghrabi.accounts.mapper;

import com.amaghrabi.accounts.dto.AccountsDto;
import com.amaghrabi.accounts.model.Accounts;

public class AccountsMapper {

    private AccountsMapper() {
    }

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
