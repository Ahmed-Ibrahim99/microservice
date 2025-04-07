package com.amaghrabi.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(
        name = "Customer",
        description = "Schema to hold Customer information"
)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDto {

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
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "01123456789"
    )
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "^01\\d{9}$", message = "Mobile number must be 11 digits")
    private String mobileNumber;
}
