package uk.co.keyoflife.standaloan;

import io.cucumber.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.http.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = StandALoanApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanCreation {
  @Value(value = "${local.server.port}")
  protected int port;
  @Autowired
  protected TestRestTemplate restTemplate;
  private Customer customer;
  private ResponseEntity<LoanCreateRequest> loanCreateRequestResponseEntity;

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
    submitNewLoanForCurrentCustomer(loanRequest);
  }

  private void submitNewLoanForCurrentCustomer(final LoanCreateRequest loanRequest) {
    loanCreateRequestResponseEntity = restTemplate.postForEntity(buildURI(), loanRequest,
                                                                 LoanCreateRequest.class);
  }

  private String buildURI() {
    return "http://localhost:" + port + "/loans";
  }

  @io.cucumber.java.en.Then("that customer has {int} loans")
  public void thatCustomerHasLoans(int numberOfLoans) {
    // TODO Make this actually check the loans stored
    assertEquals(HttpStatus.OK, loanCreateRequestResponseEntity.getStatusCode());
  }
}
