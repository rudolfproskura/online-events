package online.events.model;

import javax.persistence.*;

/**
 *
 * Korisnik entity class
 *
 */
@Entity
@Table(name = "korisnik", schema = "online_events")
public class Korisnik {

    @Id
    @Column(name = "korisnicko_ime", nullable = false, length = 20)
    private String korisnicko_ime;

    @Column(name = "ime", nullable = false, length = 50)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 50)
    private String prezime;

    @Column(name = "oib", nullable = false, length = 11)
    private String oib;

    @Column(name = "email", nullable = false, length = 11)
    private String email;


    @Column(name = "tip_korisnika", nullable = false, length = 50)
     private String tipKorisnika;

    //constructors
    public Korisnik() {
        super();
    }

    //getters & setters

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
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
}


