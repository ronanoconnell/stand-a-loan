package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.util.*;

@RestController
@RequestMapping("loans")
public class LoanController {
  @Autowired
  CustomerService customerService;

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public void createLoan(@RequestPart LoanCreateRequest loanData,
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

    customerService.createCustomer(customer, Arrays.stream(files).toList());
  }
}
