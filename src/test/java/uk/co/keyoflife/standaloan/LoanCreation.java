package uk.co.keyoflife.standaloan;

import io.cucumber.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(classes = StandALoanApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanCreation extends StandALoanSystemTests {
  @Autowired
  private CustomerRepository customerRepository;
  private Customer customer;
  private ResponseEntity<String> latestLoanCreateRequestResponseEntity;
  private final Path uploadPath = Paths.get("uploads");

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
    // Do first to ensure test documents get deleted whether it passes or fails
    final boolean document1Exists = checkDocumentUploadedAndDelete("testfile1.txt");
    final boolean document2Exists = checkDocumentUploadedAndDelete("testfile2.txt");

    assertEquals(HttpStatus.OK, latestLoanCreateRequestResponseEntity.getStatusCode());
    // TODO Switch to testing through a GET API rather than through the database directly.
    final List<Customer> allCustomers = customerRepository.findAll();
    assertNotEquals(0, allCustomers.size());

    // TODO Find the actual customer properly rather than assuming we started with an empty database
    final Customer customer1 = allCustomers.get(0);
    assertEquals("Smith", customer1.getSurName());
    final List<Loan> loans = customer1.getLoans();
    assertEquals(numberOfLoans, loans.size());
    assertEquals(1000.00, loans.get(0).getOpeningBalance());

    assertTrue(document1Exists && document2Exists);
  }

  private boolean checkDocumentUploadedAndDelete(final String fileName) {
    final File documentToCheck = uploadPath.resolve(fileName).toFile();

    if (documentToCheck.exists()) {
      documentToCheck.delete();
      return true;
    }

    return false;
  }
}
