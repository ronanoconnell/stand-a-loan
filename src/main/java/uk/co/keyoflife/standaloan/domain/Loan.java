package uk.co.keyoflife.standaloan.domain;

import jakarta.persistence.*;

import java.time.*;


@Entity
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private Double openingBalance;
  @Column(nullable = false)
  private Double currentBalance;
  @Column(nullable = false)
  private Double paymentAmount;
  @Column(nullable = false)
  private LocalDate paymentDate;
  @Column(nullable = false)
  private Double rate;
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public Loan() {
  }

  public Loan(final Double openingBalance, final Double currentBalance, final Double paymentAmount,
              final LocalDate paymentDate, final Double rate) {
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }
}
