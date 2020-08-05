package br.unesp.banco.system.money;

import java.math.BigDecimal;

public class Money implements Exchangeable {
    private Currency currency = Currency.REAL;
    private BigDecimal amount;

    public void add(Integer amount) {
        this.amount = new BigDecimal(amount).add(this.amount);
    }

    public void add(Double amount) {
        this.amount = new BigDecimal(amount).add(this.amount);
    }

    public void add(Float amount) {
        this.amount = new BigDecimal(amount).add(this.amount);
    }

    public void add(BigDecimal amount) {
        this.amount = amount.add(this.amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String toString() {
        return currency.getName() + " " + getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Money(Double amount, Currency currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money(Integer amount, Currency currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money(Float amount, Currency currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(Double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public Money(Integer amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public Money(Float amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public Money convertTo(Currency currency) {
        Money converted = new Money(0, currency);
        Double factor = this.currency.getFactor() / currency.getFactor();
        converted.add(amount.multiply(BigDecimal.valueOf(factor)));
        return converted;
    }
}
