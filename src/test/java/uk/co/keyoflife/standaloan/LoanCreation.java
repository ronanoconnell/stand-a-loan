package uk.co.keyoflife.standaloan;

import io.cucumber.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = StandALoanApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanCreation extends StandALoanSystemTests {
  @Autowired
  private CustomerRepository customerRepository;
  private Customer customer;
  private ResponseEntity<LoanCreateRequest> latestLoanCreateRequestResponseEntity;

  @io.cucumber.java.en.Given("A Customer with first name {string} and surname {string} and date of birth" +
      " {string}")
  public void createACustomer(String firstName, String surName,
                              String dateOfBirthAsString) {
    customer = new Customer(firstName, surName, LocalDate.parse(dateOfBirthAsString));

  }

  @io.cucumber.java.en.When("the a loan with opening balance {double}, current balance {double}, payment " +
      "amount {double}, payment date {string} and rate {double} is submitted for that customer")
  public void createALoanForCustomer(Double openingBalance, Double currentBalance, Double paymentAmount,
                                     String paymentDate, Double rate) {
    LoanCreateRequest loanRequest = new LoanCreateRequest(customer.getFirstName(),
                                                          customer.getSurName(),
                                                          customer.getDateOfBirth(),
                                                          openingBalance,
                                                          currentBalance,
                                                          paymentAmount,
                                                          LocalDate.parse(paymentDate), rate);
    latestLoanCreateRequestResponseEntity = submitNewLoanForCurrentCustomer(loanRequest);
  }

  @io.cucumber.java.en.Then("that customer has {int} loans")
  public void thatCustomerHasLoans(int numberOfLoans) {
    assertEquals(HttpStatus.OK, latestLoanCreateRequestResponseEntity.getStatusCode());
    // TODO Switch to testing through a GET API rather than through the database directly.
    final List<Customer> allCustomers = customerRepository.findAll();
    assertNotEquals(0, allCustomers.size());
    // TODO Find the actual customer properly rather than assuming we started with an empty database
    assertEquals(numberOfLoans, allCustomers.get(0).getLoans().size());
  }
}
