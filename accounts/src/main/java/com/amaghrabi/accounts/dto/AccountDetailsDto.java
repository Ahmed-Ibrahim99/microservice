package com.amaghrabi.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(
        name = "Customer & Account",
        description = "Schema to hold Customer and Account information"
)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDetailsDto {
    @Schema(
            description = "Name of the customer", example = "Ahmed Maghraby"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the customer name should be between 3 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "amaghrabi@ntgclarity.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "01123456789"
    )
    @Pattern(regexp = "^01\\d{9}$", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(
            description = "Account Number of Bank account", example = "3454433243"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "(^$|\\d{10})", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Bank branch address", example = "171 Haram Street, Giza"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;

    @Schema(
            description = "Account type of Bank account", example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;
}
