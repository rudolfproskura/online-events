package online.events.dto;

import java.io.Serializable;

/*
 * Data transfer object za entitet tip organizacijske jedinice
 */
public class TipOrganizacijskeJediniceDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // fields
    private Integer sifraTipOrgJedinice;
    private String nazivOrgJedinice;
    private Boolean aktivna;

    // constructors
    public TipOrganizacijskeJediniceDto() {
        super();
    }

    // getters & setters
    public Integer getSifraTipOrgJedinice() {
        return sifraTipOrgJedinice;
    }

    public void setSifraTipOrgJedinice(Integer sifraTipOrgJedinice) {
        this.sifraTipOrgJedinice = sifraTipOrgJedinice;
    }

    public String getNazivOrgJedinice() {
        return nazivOrgJedinice;
    }

    public void setNazivOrgJedinice(String nazivOrgJedinice) {
        this.nazivOrgJedinice = nazivOrgJedinice;
    }

    public Boolean getAktivna() {
        return aktivna;
    }

    public void setAktivna(Boolean aktivna) {
        this.aktivna = aktivna;
    }
}
