package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uk.co.keyoflife.standaloan.domain.*;

@Service
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

  void createCustomer(final Customer customer) {
    customerRepository.save(customer);
  }
}
