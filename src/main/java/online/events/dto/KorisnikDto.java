package online.events.dto;

import org.apache.commons.lang3.StringUtils;

public class KorisnikDto {

    private String korisnickoIme;
    private String korisnickoImeLDAP;
    private String ime;
    private String prezime;
    private String oib;
    private String email;
    private String tipKorisnika;
    private String tipKorisnikaOpis;
    private String lozinka;
    private String lozinka1;

    public KorisnikDto() {
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getTipKorisnikaOpis() {
        if (StringUtils.isNoneBlank(tipKorisnika)) {
            if (tipKorisnika.equals("admin")) {
                tipKorisnikaOpis = "Administrator";
            }
            if (tipKorisnika.equals("organizer")) {
                tipKorisnikaOpis = "Organizator";
            }
            if (tipKorisnika.equals("registredUsers")) {
                tipKorisnikaOpis = "Korisnik";
            }
        }
        return tipKorisnikaOpis;
    }

    public void setTipKorisnikaOpis(String tipKorisnikaOpis) {
        this.tipKorisnikaOpis = tipKorisnikaOpis;
    }

    public String getKorisnickoImeLDAP() {
        return korisnickoImeLDAP;
    }

    public void setKorisnickoImeLDAP(String korisnickoImeLDAP) {
        this.korisnickoImeLDAP = korisnickoImeLDAP;
    }

    public String getLozinka1() {
        return lozinka1;
    }

    public void setLozinka1(String lozinka1) {
        this.lozinka1 = lozinka1;
    }
}
