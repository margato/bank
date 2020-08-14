package br.unesp.banco.app.account;

import br.unesp.banco.core.db.stereotype.Entity;
import br.unesp.banco.app.primitives.money.Currency;
import br.unesp.banco.app.primitives.money.Money;

@Entity(table = "accounts")
public class Account  {
    private Long id;
    private String number;
    private String password;


    public Long getId() {
        return id;
    }

    public Account(String number, String password) {
        this.number = number;
        this.password = password;
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
                ", password='" + password + '\'' +
                '}';
    }
}
