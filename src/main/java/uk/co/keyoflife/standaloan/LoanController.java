package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import uk.co.keyoflife.standaloan.domain.*;
import uk.co.keyoflife.standaloan.dto.*;

@RestController
@RequestMapping("loans")
public class LoanController {
  @Autowired
  CustomerService customerService;
  @PostMapping
  public void createLoan(@RequestBody LoanCreateRequest loanData) {
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

    customerService.createCustomer(customer);
  }
}
