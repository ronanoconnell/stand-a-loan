package uk.co.keyoflife.standaloan;

import org.springframework.stereotype.*;

@Service
public class BatchProcessMonitorService {
  // TODO make this return True when batch process started.
  public Boolean hasBatchProcessStarted() {
    return Boolean.FALSE;
  }
}
