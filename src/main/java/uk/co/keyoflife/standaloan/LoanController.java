package uk.co.keyoflife.standaloan;

import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.server.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.util.*;

@RestController
@RequestMapping("loans")
public class LoanController {
  @Autowired
  CustomerService customerService;

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public void createLoan(@Valid @RequestPart LoanCreateRequest loanData,
                         @RequestPart("files") MultipartFile[] files) {
    Customer customer = new Customer(loanData.getCustomerFirstName(), loanData.getCustomerSurName(),
                                     loanData.getCustomerDateOfBirth());

    final Loan loan = new Loan(loanData.getOpeningBalance(),
                               loanData.getCurrentBalance(),
                               loanData.getPaymentAmount(),
                               loanData.getPaymentDate(),
                               loanData.getRate()
    );

    customer.addLoan(loan);
    loan.setCustomer(customer);

    if (!customerService.createCustomer(customer, Arrays.stream(files).toList())) {
      // TODO Don't just assume failure is due to service unavailabilty.
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Loan Creation Currently " +
          "unavailable. Please try again later");
    }
  }
}
