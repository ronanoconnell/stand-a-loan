package uk.co.keyoflife.standaloan.dto;


import jakarta.validation.constraints.*;

import java.time.*;

public class LoanCreateRequest {
  @NotNull
  @NotBlank
  private String customerFirstName;

  @NotNull
  @NotBlank
  @NotEmpty
  private String customerSurName;
  @NotNull
  @Past
  private LocalDate customerDateOfBirth;

  @NotNull
  @PositiveOrZero
  private final Double currentBalance;
  @NotNull
  @Positive
  private final Double paymentAmount;

  @NotNull
  @FutureOrPresent(message = "Payment Date Must be in the future")
  private final LocalDate paymentDate;
  @NotNull
  @Positive
  private final Double rate;
  @NotNull
  @Positive
  private Double openingBalance;
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

  public void setOpeningBalance(final Double openingBalance) {
    this.openingBalance = openingBalance;
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
