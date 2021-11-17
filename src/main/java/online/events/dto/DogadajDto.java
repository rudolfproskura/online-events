package online.events.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * Data transfer object za entitet dogadaj
 */
public class DogadajDto {

    // fields
    private Integer sifraDogadaja;
    private String nazivDogadaja;
    private GradDto gradDogadajaDto;
    private LocalDateTime vrijemeOd;
    private LocalDateTime vrijemeDo;
    private String slobodanUlaz;
    private Boolean slobodanUlazBoolen;
    private KorisnikDto kreatorDogadaja;
    private String korisnikDogadaj;
    private Integer tipDogadaja;

    // constructors
    public DogadajDto() {
        super();
    }

    // getters & setters
    public Integer getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(Integer sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public GradDto getGradDogadajaDto() {
        return gradDogadajaDto;
    }

    public void setGradDogadajaDto(GradDto gradDogadajaDto) {
        this.gradDogadajaDto = gradDogadajaDto;
    }

    public LocalDateTime getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(LocalDateTime vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public LocalDateTime getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(LocalDateTime vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public String getSlobodanUlaz() {
        return slobodanUlaz;
    }

    public void setSlobodanUlaz(String slobodanUlaz) {
        this.slobodanUlaz = slobodanUlaz;
    }

    public KorisnikDto getKreatorDogadaja() {
        return kreatorDogadaja;
    }

    public void setKreatorDogadaja(KorisnikDto kreatorDogadaja) {
        this.kreatorDogadaja = kreatorDogadaja;
    }

    public Boolean getSlobodanUlazBoolen() {
        return slobodanUlazBoolen;
    }

    public void setSlobodanUlazBoolen(Boolean slobodanUlazBoolen) {
        this.slobodanUlazBoolen = slobodanUlazBoolen;
    }

    public String getKorisnikDogadaj() {
        return korisnikDogadaj;
    }

    public void setKorisnikDogadaj(String korisnikDogadaj) {
        this.korisnikDogadaj = korisnikDogadaj;
    }

    public Integer getTipDogadaja() {
        return tipDogadaja;
    }

    public void setTipDogadaja(Integer tipDogadaja) {
        this.tipDogadaja = tipDogadaja;
    }
}
