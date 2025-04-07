package com.amaghrabi.accounts.controller;

import com.amaghrabi.accounts.dto.AccountDetailsDto;
import com.amaghrabi.accounts.dto.AccountsDto;
import com.amaghrabi.accounts.dto.ErrorResponseDto;
import com.amaghrabi.accounts.dto.ResponseDto;
import com.amaghrabi.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Account",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE Account"
)
@Validated
@RestController
@RequestMapping(path = "/accounts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountsController {

    private final AccountsService accountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new Account",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HTTP Status CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountsDto accountsDto) {
        accountsService.createAccount(accountsDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201",
                        "Account created successfully"));
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to delete Account based on a account number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "Expectation Failed"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(
            @Pattern(regexp = "(^$|\\d{10})", message = "AccountNumber must be 10 digits")
            @RequestParam Long accountNumber) {
        boolean isDeleted = accountsService.deleteAccount(accountNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200",
                            "Account deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto("417",
                        "Update operation failed. Please try again or contact Dev team"));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer & Account details based on a mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetch-account-details")
    public ResponseEntity<AccountDetailsDto> fetchAccountDetails(
            @Pattern(regexp = "^01\\d{9}$", message = "Mobile number must be 11 digits")
            @RequestParam String mobileNumber) {
        AccountDetailsDto accountDetailsDto = accountsService.fetchAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(accountDetailsDto);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "REST API to update Customer based on mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "Expectation Failed"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PutMapping("/update-account-details")
    public ResponseEntity<ResponseDto> updateAccountDetails(
            @Valid @RequestBody AccountDetailsDto accountDetailsDto) {
        boolean isUpdated = accountsService.updateAccountDetails(accountDetailsDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto("200",
                            "Request processed successfully"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto("417",
                        "Update operation failed. Please try again or contact Dev team"));
    }


}
