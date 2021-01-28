package online.events.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    private Integer sifraTipKorisnika;

    @NotNull @Size(min = 1, max = 50)
    @Column(name = "naziv", nullable = true, length = 50)
    private String nazivTipKorisnika;

    @Size(min = 1, max = 50)
    @Column(name = "opis", nullable = true, length = 100)
    private String opisTipKorisnika;

    @NotNull
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


