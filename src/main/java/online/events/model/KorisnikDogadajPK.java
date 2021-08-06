package online.events.model;

import java.io.Serializable;

public class KorisnikDogadajPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String korisnik;
    private Integer dogadaj;

    public KorisnikDogadajPK(String korisnik, Integer dogadaj) {
        this.korisnik = korisnik;
        this.dogadaj = dogadaj;
    }
}
