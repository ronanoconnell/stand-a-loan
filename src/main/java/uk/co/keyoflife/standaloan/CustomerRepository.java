package uk.co.keyoflife.standaloan;

import org.springframework.data.jpa.repository.*;
import uk.co.keyoflife.standaloan.domain.*;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
