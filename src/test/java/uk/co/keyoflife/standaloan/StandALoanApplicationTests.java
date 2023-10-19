package uk.co.keyoflife.standaloan;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StandALoanApplicationTests extends StandALoanSystemTests {

  @Test
  void contextLoads() {
  }

  @Test
  void postLoanCreation() {
    final LoanCreateRequest requestToPost = new LoanCreateRequest("Anne",
                                                                  "Smith",
                                                                  LocalDate.parse("2000-01-01"),
                                                                  1000.00, 900.00,
                                                                  100.00,
                                                                  LocalDate.parse("2024-12-12"),
                                                                  4.50);

    assertEquals(HttpStatus.OK, submitNewLoanForCurrentCustomer(requestToPost).getStatusCode());
  }

  @Test
  void postLoanRequestWithoutAllRequiredData() {
    final LoanCreateRequest requestToPost = new LoanCreateRequest("Anne",
                                                                  "",
                                                                  LocalDate.parse("2000-01-01"),
                                                                  1000.00, 900.00,
                                                                  100.00,
                                                                  LocalDate.parse("2024-12-12"),
                                                                  4.50);

    assertEquals(HttpStatus.BAD_REQUEST, submitNewLoanForCurrentCustomer(requestToPost).getStatusCode());
  }

  @Test
  void postLoanRequestWithFutureCustomerDateOfBirth() {
    final LoanCreateRequest requestToPost = new LoanCreateRequest("Anne",
                                                                  "Smith",
                                                                  LocalDate.parse("2200-01-01"),
                                                                  1000.00, 900.00,
                                                                  100.00,
                                                                  LocalDate.parse("2024-12-12"),
                                                                  4.50);

    assertEquals(HttpStatus.BAD_REQUEST, submitNewLoanForCurrentCustomer(requestToPost).getStatusCode());
  }

  @Test
  void postLoanRequestWithPastPaymentDate() {
    final LoanCreateRequest requestToPost = new LoanCreateRequest("Anne",
                                                                  "Smith",
                                                                  LocalDate.parse("2000-01-01"),
                                                                  1000.00, 900.00,
                                                                  100.00,
                                                                  LocalDate.parse("2010-12-12"),
                                                                  4.50);

    assertEquals(HttpStatus.BAD_REQUEST, submitNewLoanForCurrentCustomer(requestToPost).getStatusCode());
  }

  @Test
  void postLoanRequestWithZeroOpeningBalance() {
    final LoanCreateRequest requestToPost = new LoanCreateRequest("Anne",
                                                                  "Smith",
                                                                  LocalDate.parse("2000-01-01"),
                                                                  0.00, 900.00,
                                                                  100.00,
                                                                  LocalDate.parse("2034-12-12"),
                                                                  4.50);

    assertEquals(HttpStatus.BAD_REQUEST, submitNewLoanForCurrentCustomer(requestToPost).getStatusCode());
  }
}
