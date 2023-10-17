package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;
import uk.co.keyoflife.standaloan.domain.*;

import java.util.*;

@Service
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private DocumentStorageService documentStorage;

  @Autowired
  private BatchProcessMonitorService batchProcessMonitorService;

  void createCustomer(final Customer customer, final List<MultipartFile> documents) {
    int documentsStored = 0;
    if (!checkForBatchProcessStart()) {
      for (MultipartFile document : documents) {
        if (!documentStorage.store(document) || checkForBatchProcessStart())
          break;

        documentsStored++;
      }
    }

    if (documentsStored == documents.size() && !checkForBatchProcessStart())
      customerRepository.save(customer);
  }

  private Boolean checkForBatchProcessStart() {
    return batchProcessMonitorService.hasBatchProcessStarted();
  }
}
