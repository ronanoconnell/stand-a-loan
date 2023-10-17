package uk.co.keyoflife.standaloan;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.mock.web.*;
import org.springframework.web.multipart.*;
import uk.co.keyoflife.standaloan.domain.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class CustomerServiceTest {
  @Mock
  private CustomerRepository mockCustomerRepository;

  @Mock
  private DocumentStorageService mockDocumentStorageService;

  @InjectMocks
  private CustomerService serviceUnderTest;

  private Customer testCustomer;
  private List<MultipartFile> documentList;

  @BeforeEach
  private void setUp() {
    MockitoAnnotations.openMocks(this);

    testCustomer = new Customer();

    final MockMultipartFile testDocument = new MockMultipartFile("blah", new byte[1]);
    documentList = new ArrayList<>();
    documentList.add(testDocument);
    documentList.add(testDocument);
  }

  @Test
  void CustomerLoanStore() {
    testCustomer.setFirstName("Anne");

    Mockito.when(mockDocumentStorageService.store(any())).thenReturn(true);
    serviceUnderTest.createCustomer(testCustomer, new ArrayList<>());

    Mockito.verify(mockCustomerRepository).save(testCustomer);
  }

  @Test
  void CustomerLoanAndDocumentsStore() {
    Mockito.when(mockDocumentStorageService.store(any())).thenReturn(true);
    serviceUnderTest.createCustomer(testCustomer, documentList);

    Mockito.verify(mockDocumentStorageService, times(2)).store(any());
  }

  @Test
  void CustomerLoanOnlySavedAfterDocumentsStored() {
    Mockito.when(mockDocumentStorageService.store(any())).thenReturn(true);
    serviceUnderTest.createCustomer(testCustomer, documentList);
    InOrder orderVerifier = Mockito.inOrder(mockDocumentStorageService, mockCustomerRepository);
    orderVerifier.verify(mockDocumentStorageService, times(2)).store(any());
    orderVerifier.verify(mockCustomerRepository).save(testCustomer);
  }

  @Test
  void CustomerLoanNotSavedIfAnyDocumentsNotStored() {
    serviceUnderTest.createCustomer(testCustomer, documentList);
    Mockito.when(mockDocumentStorageService.store(any())).thenReturn(false);

    Mockito.verify(mockCustomerRepository, times(0)).save(testCustomer);
  }
}