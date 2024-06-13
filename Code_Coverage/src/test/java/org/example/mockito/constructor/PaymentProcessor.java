package org.example.mockito.constructor;

import java.math.BigDecimal;

public class PaymentProcessor {

  private final boolean allowForeignCurrencies;
  private final String fallbackOption;
  private final BigDecimal taxRate;

  public PaymentProcessor() {
    this(false, "DEBIT", new BigDecimal("19.00"));
  }

  public PaymentProcessor(String fallbackOption, BigDecimal taxRate) {
    this(false, fallbackOption, taxRate);
  }

  public PaymentProcessor(boolean allowForeignCurrencies, String fallbackOption, BigDecimal taxRate) {
    this.allowForeignCurrencies = allowForeignCurrencies;
    this.fallbackOption = fallbackOption;
    this.taxRate = taxRate;
  }

  public BigDecimal chargeCustomer(String customerId, BigDecimal netPrice) {

    // Any arbitrary implementation
    System.out.println("About to charge customer: " + customerId);
    return BigDecimal.ZERO;
  }
}
