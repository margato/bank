package br.unesp.banco.app.primitives.money;

public enum Currency  {
    DOLLAR(1.0, "$"), REAL(0.19, "R$"), EURO(1.18, "€"), YEN(0.0095, "¥");

    /***
     * Math relation between different currencies.
     * Reference: Dollar
     */
    private final Double factor;
    private final String name;

    Currency(Double factor, String name) {
        this.factor = factor;
        this.name = name;
    }

    public Double getFactor() {
        return factor;
    }

    public String getName() {
        return name;
    }

}
