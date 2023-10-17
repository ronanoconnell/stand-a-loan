package uk.co.keyoflife.standaloan;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.util.*;
import uk.co.keyoflife.standaloan.dto.*;

import java.nio.file.*;

public class StandALoanSystemTests {
  @Value(value = "${local.server.port}")
  protected int port;
  @Autowired
  protected TestRestTemplate restTemplate;
  final private Path testFilePath = Paths.get("testfiles");

  protected ResponseEntity<String> submitNewLoanForCurrentCustomer(final LoanCreateRequest loanRequest) {
    MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("loanData", loanRequest);
    requestBody.add("files", new FileSystemResource(testFilePath.resolve("testfile1.txt")));
    requestBody.add("files", new FileSystemResource(testFilePath.resolve("testfile2.txt")));

    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestBody, headers);

    return restTemplate.postForEntity(buildURI(), request, String.class);
  }

  private String buildURI() {
    return "http://localhost:" + port + "/loans";
  }
}
