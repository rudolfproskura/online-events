package online.events.model;

import javax.persistence.*;

/**
 *
 * Organizacijska jedinica entity class
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "organizacijska_jedinica", schema = "igea_tk")
public class OrganizacijskaJedinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sifra", nullable = false)
    private Integer sifraOrgJedinice;

    @Column(name = "naziv", nullable = false, length = 50)
    private String nazivOrgJedinice;

    @Column(name = "opis", nullable = true, length = 200)
    private String opisOrgJedinice;

    @JoinColumn(name = "tip_org_jedinice", referencedColumnName = "sifra")
    @ManyToOne
    private TipOrganizacijskeJedinice tipOrganizacijskeJedinice;

    @JoinColumn(name = "org_jedinica", referencedColumnName = "sifra")
    @ManyToOne
    private OrganizacijskaJedinica nadredenaOrgJedinica;


    //constructors

    public OrganizacijskaJedinica() {
    }


    //getters & setters
    public Integer getSifraOrgJedinice() {
        return sifraOrgJedinice;
    }

    public void setSifraOrgJedinice(Integer sifraOrgJedinice) {
        this.sifraOrgJedinice = sifraOrgJedinice;
    }

    public String getNazivOrgJedinice() {
        return nazivOrgJedinice;
    }

    public void setNazivOrgJedinice(String nazivOrgJedinice) {
        this.nazivOrgJedinice = nazivOrgJedinice;
    }

    public String getOpisOrgJedinice() {
        return opisOrgJedinice;
    }

    public void setOpisOrgJedinice(String opisOrgJedinice) {
        this.opisOrgJedinice = opisOrgJedinice;
    }

    public TipOrganizacijskeJedinice getTipOrganizacijskeJedinice() {
        return tipOrganizacijskeJedinice;
    }

    public void setTipOrganizacijskeJedinice(TipOrganizacijskeJedinice tipOrganizacijskeJedinice) {
        this.tipOrganizacijskeJedinice = tipOrganizacijskeJedinice;
    }

    public OrganizacijskaJedinica getNadredenaOrgJedinica() {
        return nadredenaOrgJedinica;
    }

    public void setNadredenaOrgJedinica(OrganizacijskaJedinica nadredenaOrgJedinica) {
        this.nadredenaOrgJedinica = nadredenaOrgJedinica;
    }
}