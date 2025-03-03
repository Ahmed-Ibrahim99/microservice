package com.amaghrabi.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDto {
    @Schema(
            description = "Customer id for account", example = "1"
    )
    @NotEmpty(message = "Customer Id can not be null or empty")
    private Long customerId;

    @Schema(
            description = "Account type of Bank account", example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            description = "Account Number of Bank account", example = "3454433243"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    private String accountNumber;

    @Schema(
            description = "Bank branch address", example = "171 Haram Street, Giza"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
