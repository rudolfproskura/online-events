package online.events.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Data transfer object za entitet dogadaj
 */
public class DogadajFilterDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // fields
    private Integer sifraDogadaja;
    private String nazivDogadaja;
    private String slobodanUlaz;
    private Integer sifraGrada;
    private String nazivGrada;
    private Integer velicinaGrada;

    //razdoblje pocetka
    private LocalDateTime vrijemeOdPocetak;
    private LocalDateTime vrijemeDoPocetak;

    //razdoblje kraja
    private LocalDateTime vrijemeOdKraj;
    private LocalDateTime vrijemeDoKraj;

    private List<Integer> zupanije = new ArrayList<>();
    private List<Integer> regije = new ArrayList<>();
    private List<Integer> gradovi = new ArrayList<>();

    private String[] odabraniGradovi;
    private String[] odabraneRegije;
    private String[] odabraneZupanije;
    private String[] odabraneVelicineGrada;

    // constructors
    public DogadajFilterDto() {
        super();
    }

    public DogadajFilterDto(Integer sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }
// getters & setters

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public String getSlobodanUlaz() {
        return slobodanUlaz;
    }

    public void setSlobodanUlaz(String slobodanUlaz) {
        this.slobodanUlaz = slobodanUlaz;
    }

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

    public Integer getVelicinaGrada() {
        return velicinaGrada;
    }

    public void setVelicinaGrada(Integer velicinaGrada) {
        this.velicinaGrada = velicinaGrada;
    }

    public LocalDateTime getVrijemeOdPocetak() {
        return vrijemeOdPocetak;
    }

    public void setVrijemeOdPocetak(LocalDateTime vrijemeOdPocetak) {
        this.vrijemeOdPocetak = vrijemeOdPocetak;
    }

    public LocalDateTime getVrijemeDoPocetak() {
        return vrijemeDoPocetak;
    }

    public void setVrijemeDoPocetak(LocalDateTime vrijemeDoPocetak) {
        this.vrijemeDoPocetak = vrijemeDoPocetak;
    }

    public LocalDateTime getVrijemeOdKraj() {
        return vrijemeOdKraj;
    }

    public void setVrijemeOdKraj(LocalDateTime vrijemeOdKraj) {
        this.vrijemeOdKraj = vrijemeOdKraj;
    }

    public LocalDateTime getVrijemeDoKraj() {
        return vrijemeDoKraj;
    }

    public void setVrijemeDoKraj(LocalDateTime vrijemeDoKraj) {
        this.vrijemeDoKraj = vrijemeDoKraj;
    }

    public List<Integer> getZupanije() {
        return zupanije;
    }

    public void setZupanije(List<Integer> zupanije) {
        this.zupanije = zupanije;
    }

    public List<Integer> getRegije() {
        return regije;
    }

    public void setRegije(List<Integer> regije) {
        this.regije = regije;
    }

    public List<Integer> getGradovi() {
        return gradovi;
    }

    public void setGradovi(List<Integer> gradovi) {
        this.gradovi = gradovi;
    }

    public String[] getOdabraniGradovi() {
        return odabraniGradovi;
    }

    public void setOdabraniGradovi(String[] odabraniGradovi) {
        this.odabraniGradovi = odabraniGradovi;
    }

    public String[] getOdabraneRegije() {
        return odabraneRegije;
    }

    public void setOdabraneRegije(String[] odabraneRegije) {
        this.odabraneRegije = odabraneRegije;
    }

    public String[] getOdabraneZupanije() {
        return odabraneZupanije;
    }

    public void setOdabraneZupanije(String[] odabraneZupanije) {
        this.odabraneZupanije = odabraneZupanije;
    }

    public String[] getOdabraneVelicineGrada() {
        return odabraneVelicineGrada;
    }

    public void setOdabraneVelicineGrada(String[] odabraneVelicineGrada) {
        this.odabraneVelicineGrada = odabraneVelicineGrada;
    }

    public Integer getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(Integer sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }
}
