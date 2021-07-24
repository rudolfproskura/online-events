package online.events.dto;

import java.io.Serializable;

/*
 * Data transfer object za entitet grad
 */
public class GradDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // fields
    private Integer sifraGrada;
    private String nazivGrada;
    private VelicinaGradaDto velicinaGradaDto;
    private OrganizacijskaJedinicaDto organizacijskaJedinicaDto;

    // constructors
    public GradDto() {
        super();
    }

    // getters & setters
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

    public VelicinaGradaDto getVelicinaGradaDto() {
        return velicinaGradaDto;
    }

    public void setVelicinaGradaDto(VelicinaGradaDto velicinaGradaDto) {
        this.velicinaGradaDto = velicinaGradaDto;
    }

    public OrganizacijskaJedinicaDto getOrganizacijskaJedinicaDto() {
        return organizacijskaJedinicaDto;
    }

    public void setOrganizacijskaJedinicaDto(OrganizacijskaJedinicaDto organizacijskaJedinicaDto) {
        this.organizacijskaJedinicaDto = organizacijskaJedinicaDto;
    }
}
