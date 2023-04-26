package com.example.rpkasa2;

import java.io.Serializable;

public class product implements Serializable {
    String nazev;
    int cena;

    public product(String nazev, int cena) {
        this.nazev = nazev;
        this.cena = cena;
    }
}
