package com.amaghrabi.accounts.service;

import com.amaghrabi.accounts.dto.CustomerDto;
import com.amaghrabi.accounts.exception.CustomerAlreadyExistsException;
import com.amaghrabi.accounts.exception.ResourceNotFoundException;
import com.amaghrabi.accounts.mapper.CustomerMapper;
import com.amaghrabi.accounts.model.Customer;
import com.amaghrabi.accounts.repository.AccountsRepository;
import com.amaghrabi.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;

    public void createCustomer(CustomerDto customerDto) {
        try {
            if (customerDto == null) {
                throw new IllegalArgumentException("CustomerDto cannot be null");
            }
            Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
            Optional<Customer> customerExists = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
            if (customerExists.isPresent()) {
                throw new CustomerAlreadyExistsException("Customer already exists");
            }
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create customer", e);
        }
    }


    public boolean updateCustomer(String mobileNumber, CustomerDto customerDto) {
        boolean isUpdated = false;
        if (customerDto != null) {
            Customer customer = customerRepository
                    .findCustomerByMobileNumber(mobileNumber)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Customer",
                                    "CustomerId",
                                    customerDto.getMobileNumber())
                    );

            Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(updatedCustomer);

            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteCustomer(Long customerId) {
        boolean isDeleted = false;
        if (customerId != null) {
            customerRepository.deleteById(customerId);
            accountsRepository.deleteByCustomerId(customerId);

            isDeleted = true;
        }
        return isDeleted;
    }
}
