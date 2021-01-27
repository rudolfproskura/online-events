package online.events.model;

import javax.persistence.*;

/**
 *
 * Velicina grada entity class
 *
 */
@Entity
@Table(name = "velicina_grada", schema = "online_events")
public class VelicinaGrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sifra", nullable = false)
    private Integer sifraVelicineGrada;

    @Column(name = "naziv", nullable = true, length = 50)
    private String nazivVelicineGrada;

    @Column(name = "aktivan", nullable = false)
    private Boolean aktivanVelicinaGrada;


    //constructors
    public VelicinaGrada() {
        super();
    }

    //getters & setters
    public Integer getSifraVelicineGrada() {
        return sifraVelicineGrada;
    }

    public void setSifraVelicineGrada(Integer sifraVelicineGrada) {
        this.sifraVelicineGrada = sifraVelicineGrada;
    }

    public String getNazivVelicineGrada() {
        return nazivVelicineGrada;
    }

    public void setNazivVelicineGrada(String nazivVelicineGrada) {
        this.nazivVelicineGrada = nazivVelicineGrada;
    }

    public Boolean getAktivanVelicinaGrada() {
        return aktivanVelicinaGrada;
    }

    public void setAktivanVelicinaGrada(Boolean aktivanVelicinaGrada) {
        this.aktivanVelicinaGrada = aktivanVelicinaGrada;
    }

}


