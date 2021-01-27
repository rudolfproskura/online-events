package online.events.model;

import javax.persistence.*;

/**
 *
 * Tip korisnika entity class
 *
 */
@Entity
@Table(name = "tip_korisnika", schema = "online_events")
public class TipKorisnika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sifra", nullable = false)
    private Integer sifraTipKorisnika;

    @Column(name = "naziv", nullable = true, length = 50)
    private String nazivTipKorisnika;

    @Column(name = "aktivan", nullable = false)
    private Boolean aktivanTipKorisnika;


    //constructors
    public TipKorisnika() {
        super();
    }

    //getters & setters


    public Integer getSifraTipKorisnika() {
        return sifraTipKorisnika;
    }

    public void setSifraTipKorisnika(Integer sifraTipKorisnika) {
        this.sifraTipKorisnika = sifraTipKorisnika;
    }

    public String getNazivTipKorisnika() {
        return nazivTipKorisnika;
    }

    public void setNazivTipKorisnika(String nazivTipKorisnika) {
        this.nazivTipKorisnika = nazivTipKorisnika;
    }

    public Boolean getAktivanTipKorisnika() {
        return aktivanTipKorisnika;
    }

    public void setAktivanTipKorisnika(Boolean aktivanTipKorisnika) {
        this.aktivanTipKorisnika = aktivanTipKorisnika;
    }
}


