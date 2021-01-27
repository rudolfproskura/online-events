package online.events.model;

import javax.persistence.*;

/**
 *
 * Tip organizacijske jedinice entity class
 *
 */
@Entity
@Table(name = "tip_organizacijske_jedinice", schema = "online_events")
public class TipOrganizacijskeJedinice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sifra", nullable = false)
    private Integer sifraTipOrgJedinice;

    @Column(name = "naziv", nullable = true, length = 50)
    private String nazivTipOrgJedinice;

    @Column(name = "aktivan", nullable = false)
    private Boolean aktivanTipOrgJedinice;


    //constructors
    public TipOrganizacijskeJedinice() {
        super();
    }

    //getters & setters
    public Integer getSifraTipOrgJedinice() {
        return sifraTipOrgJedinice;
    }

    public void setSifraTipOrgJedinice(Integer sifraTipOrgJedinice) {
        this.sifraTipOrgJedinice = sifraTipOrgJedinice;
    }

    public String getNazivTipOrgJedinice() {
        return nazivTipOrgJedinice;
    }

    public void setNazivTipOrgJedinice(String nazivTipOrgJedinice) {
        this.nazivTipOrgJedinice = nazivTipOrgJedinice;
    }

    public Boolean getAktivanTipOrgJedinice() {
        return aktivanTipOrgJedinice;
    }

    public void setAktivanTipOrgJedinice(Boolean aktivanTipOrgJedinice) {
        this.aktivanTipOrgJedinice = aktivanTipOrgJedinice;
    }
}


