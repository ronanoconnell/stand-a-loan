package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.http.*;
import uk.co.keyoflife.standaloan.dto.*;

public class StandALoanSystemTests {
  @Value(value = "${local.server.port}")
  protected int port;
  @Autowired
  protected TestRestTemplate restTemplate;

  protected ResponseEntity<LoanCreateRequest> submitNewLoanForCurrentCustomer(final LoanCreateRequest loanRequest) {
    return restTemplate.postForEntity(buildURI(), loanRequest, LoanCreateRequest.class);
  }

  private String buildURI() {
    return "http://localhost:" + port + "/loans";
  }
}
