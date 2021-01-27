package online.events.model;

import javax.persistence.*;

/**
 *
 * Velicina grada entity class
 *
 */
@Entity
@Table(name = "grad", schema = "igea_tk")
public class Grad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sifra", nullable = false)
    private Integer sifraGrada;

    @Column(name = "naziv", nullable = false, length = 50)
    private String nazivGrada;

    @JoinColumn(name = "velicina", referencedColumnName = "sifra")
    @ManyToOne
    private VelicinaGrada velicinaGrada;

    @JoinColumn(name = "org_jedinica", referencedColumnName = "sifra")
    @ManyToOne
    private OrganizacijskaJedinica organizacijskaJedinica;

    //constructors
    public Grad() {
        super();
    }

    //getters & setters
    public Integer getSifraGrada() {
        return sifraGrada;
    }

    public void setSifraGrada(Integer sifraGrada) {
        this.sifraGrada = sifraGrada;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public void setNazivGrada(String nazivGrada) {
        this.nazivGrada = nazivGrada;
    }

    public VelicinaGrada getVelicinaGrada() {
        return velicinaGrada;
    }

    public void setVelicinaGrada(VelicinaGrada velicinaGrada) {
        this.velicinaGrada = velicinaGrada;
    }

    public OrganizacijskaJedinica getOrganizacijskaJedinica() {
        return organizacijskaJedinica;
    }

    public void setOrganizacijskaJedinica(OrganizacijskaJedinica organizacijskaJedinica) {
        this.organizacijskaJedinica = organizacijskaJedinica;
    }
}


