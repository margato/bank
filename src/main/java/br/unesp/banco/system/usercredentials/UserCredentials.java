package br.unesp.banco.system.usercredentials;

public class UserCredentials {
    private String account_num;
    private Long id;

    public UserCredentials(String account_num, Long id) {
        this.account_num = account_num;
        this.id = id;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
