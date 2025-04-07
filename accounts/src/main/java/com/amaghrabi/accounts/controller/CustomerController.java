package com.amaghrabi.accounts.controller;

import com.amaghrabi.accounts.dto.CustomerDto;
import com.amaghrabi.accounts.dto.ErrorResponseDto;
import com.amaghrabi.accounts.dto.ResponseDto;
import com.amaghrabi.accounts.service.CustomerService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Customer",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE Customer"
)
@Validated
@RestController
@RequestMapping(path = "/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Create Customer REST API",
            description = "REST API to create a new Customer",
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
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201",
                        "Customer created successfully"));

    }

    @Operation(
            summary = "Update Customer REST API",
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
    @PutMapping
    public ResponseEntity<ResponseDto> updateCustomer(
            @Pattern(regexp = "^01\\d{9}$", message = "Mobile number must be 11 digits")
            @RequestParam String mobileNumber,
            @Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = customerService.updateCustomer(mobileNumber, customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200",
                            "Customer updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto("417",
                        "Update operation failed. Please try again or contact Dev team"));

    }

    @Operation(
            summary = "Delete Customer & Account Related to it REST API",
            description = "REST API to delete Customer & Account based on customer id",
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
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam Long customerId) {
        boolean isDeleted = customerService.deleteCustomer(customerId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200",
                            "Customer and Account deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto("417",
                        "Update operation failed. Please try again or contact Dev team"));
    }
}
