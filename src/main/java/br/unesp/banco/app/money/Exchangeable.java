package br.unesp.banco.app.money;

public interface Exchangeable {
    Money convertTo(Currency currency);
}
