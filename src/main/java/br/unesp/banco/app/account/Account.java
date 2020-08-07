package br.unesp.banco.app.account;

import br.unesp.banco.core.db.stereotype.Entity;
import br.unesp.banco.app.money.Currency;
import br.unesp.banco.app.money.Money;

@Entity(table = "accounts")
public class Account  {
    private Long id;
    private String number;
    private String password;
    private Money balance;

    public Long getId() {
        return id;
    }

    public Account(String number, String password) {
        this.number = number;
        this.password = password;
        balance = new Money(0, Currency.REAL);
    }

    public Account() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
