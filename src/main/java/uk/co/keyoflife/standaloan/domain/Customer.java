package uk.co.keyoflife.standaloan.domain;

import jakarta.persistence.*;

import java.time.*;

@Entity
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String surName;
  private LocalDate dateOfBirth;

  public Customer(final String firstName, final String surName, final LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.surName = surName;
    this.dateOfBirth = dateOfBirth;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getSurName() {
    return surName;
  }

  public void setSurName(final String surName) {
    this.surName = surName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(final LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
