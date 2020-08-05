package br.unesp.banco.system.money;

public interface Exchangeable {
    Money convertTo(Currency currency);
}
