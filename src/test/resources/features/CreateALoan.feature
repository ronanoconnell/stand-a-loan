Feature: Create a loan for a customer

  Scenario: A valid loan is submitted
    Given A Customer with first name "Anne" and surname "Smith" and date of birth "2000-01-01"
    When the a loan with opening balance 1000.00, current balance 900.00, payment amount 100.00, payment date "2024-12-12" and rate 4.50 is submitted for that customer
    Then that customer has 1 loans