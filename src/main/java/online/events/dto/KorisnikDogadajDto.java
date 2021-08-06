package online.events.dto;

import java.io.Serializable;

public class KorisnikDogadajDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private KorisnikDto korisnikDto;
    private DogadajDto dogadajDto;

    public KorisnikDogadajDto() {
    }

    public KorisnikDto getKorisnikDto() {
        return korisnikDto;
    }

    public void setKorisnikDto(KorisnikDto korisnikDto) {
        this.korisnikDto = korisnikDto;
    }

    public DogadajDto getDogadajDto() {
        return dogadajDto;
    }

    public void setDogadajDto(DogadajDto dogadajDto) {
        this.dogadajDto = dogadajDto;
    }
}
