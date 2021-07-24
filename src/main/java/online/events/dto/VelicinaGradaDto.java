package online.events.dto;

import java.io.Serializable;

/*
 * Data transfer object za entitet velicina grada
 */
public class VelicinaGradaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    // fields
    private Integer sifraVelicineGrada;
    private String nazivVelicineGrada;
    private Boolean aktivan;

    // constructors
    public VelicinaGradaDto() {
        super();
    }

    // getters & setters
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

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }
}
