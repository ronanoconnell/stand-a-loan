package uk.co.keyoflife.standaloan.dto;

import java.time.*;

public class LoanCreateRequest {
  private final Double openingBalance;
  private final Double currentBalance;
  private final Double paymentAmount;
  private final LocalDate paymentDate;
  private final Double rate;
  private String customerFirstName;
  private String customerSurName;
  private LocalDate customerDateOfBirth;

  public LoanCreateRequest(final String customerFirstName, final String customerSurName,
                           final LocalDate customerDateOfBirth, final Double openingBalance,
                           final Double currentBalance, final Double paymentAmount,
                           final LocalDate paymentDate, final Double rate) {
    this.customerFirstName = customerFirstName;
    this.customerSurName = customerSurName;
    this.customerDateOfBirth = customerDateOfBirth;
    this.openingBalance = openingBalance;
    this.currentBalance = currentBalance;
    this.paymentAmount = paymentAmount;
    this.paymentDate = paymentDate;
    this.rate = rate;
  }

  public Double getOpeningBalance() {
    return openingBalance;
  }

  public Double getCurrentBalance() {
    return currentBalance;
  }

  public Double getPaymentAmount() {
    return paymentAmount;
  }

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public Double getRate() {
    return rate;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(final String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerSurName() {
    return customerSurName;
  }

  public void setCustomerSurName(final String customerSurName) {
    this.customerSurName = customerSurName;
  }

  public LocalDate getCustomerDateOfBirth() {
    return customerDateOfBirth;
  }

  public void setCustomerDateOfBirth(final LocalDate customerDateOfBirth) {
    this.customerDateOfBirth = customerDateOfBirth;
  }
}
