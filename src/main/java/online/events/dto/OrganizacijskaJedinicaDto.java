package online.events.dto;

import java.io.Serializable;

/*
 * Data transfer object za entitet organizacijsku jedinicu
 */
public class OrganizacijskaJedinicaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // fields
    private Integer sifraOrgJedinice;
    private String nazivOrgJedinice;
    private String opisOrgJedinice;
    private TipOrganizacijskeJediniceDto tipOrganizacijskeJediniceDto;
    private OrganizacijskaJedinicaDto nadredenaOrganizacijeDto;

    // constructors
    public OrganizacijskaJedinicaDto() {
        super();
    }

    // getters & setters
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

    public TipOrganizacijskeJediniceDto getTipOrganizacijskeJediniceDto() {
        return tipOrganizacijskeJediniceDto;
    }

    public void setTipOrganizacijskeJediniceDto(TipOrganizacijskeJediniceDto tipOrganizacijskeJediniceDto) {
        this.tipOrganizacijskeJediniceDto = tipOrganizacijskeJediniceDto;
    }

    public OrganizacijskaJedinicaDto getNadredenaOrganizacijeDto() {
        return nadredenaOrganizacijeDto;
    }

    public void setNadredenaOrganizacijeDto(OrganizacijskaJedinicaDto nadredenaOrganizacijeDto) {
        this.nadredenaOrganizacijeDto = nadredenaOrganizacijeDto;
    }
}
