package online.events.model;


import javax.persistence.*;

@Entity
@IdClass(KorisnikDogadajPK.class)
@Table(name = "korisnik_dogadaj", schema = "online_events")
public class KorisnikDogadaj {

    @Id
    @JoinColumn(name = "korisnik", referencedColumnName = "korisnicko_ime")
    @ManyToOne
    private Korisnik korisnik;

    @Id
    @JoinColumn(name = "dogadaj", referencedColumnName = "sifra")
    @ManyToOne
    private Dogadaj dogadaj;

    public KorisnikDogadaj() {
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Dogadaj getDogadaj() {
        return dogadaj;
    }

    public void setDogadaj(Dogadaj dogadaj) {
        this.dogadaj = dogadaj;
    }
}
