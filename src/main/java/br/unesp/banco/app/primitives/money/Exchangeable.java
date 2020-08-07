package br.unesp.banco.app.primitives.money;

public interface Exchangeable {
    Money convertTo(Currency currency);
}
