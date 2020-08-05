package br.unesp.banco.system.usercredentials;

public class UserCredentials {
    private String accountNumber;
    private Long id;

    public UserCredentials(String accountNumber, Long id) {
        this.accountNumber = accountNumber;
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
