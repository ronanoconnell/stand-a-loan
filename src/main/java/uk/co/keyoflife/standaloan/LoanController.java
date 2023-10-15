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
  public void createLoan(LoanCreateRequest request) {
    Customer customer = new Customer(request.getCustomerFirstName(), request.getCustomerSurName(),
                                     request.getCustomerDateOfBirth());

    final Loan loan = new Loan(request.getOpeningBalance(),
                               request.getCurrentBalance(),
                               request.getPaymentAmount(),
                               request.getPaymentDate(),
                               request.getRate()
    );

    customer.addLoan(loan);
    loan.setCustomer(customer);

    customerService.createCustomer(customer);
  }
}
