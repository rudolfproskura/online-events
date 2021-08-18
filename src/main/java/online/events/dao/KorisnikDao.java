package online.events.dao;


import online.events.dto.KorisnikDto;
import online.events.model.Korisnik;

import java.io.Serializable;


public class KorisnikDao extends GenericDao<Korisnik, KorisnikDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected Korisnik formEntity(KorisnikDto dto) {
        return null;//TODO
    }

    @Override
    protected KorisnikDto formDTO(Korisnik entity) {
        KorisnikDto korisnikDto = null;
        if (entity != null) {
            korisnikDto = new KorisnikDto();
            Korisnik korinikEntity = (Korisnik) entity;
            korisnikDto.setKorisnickoIme(korinikEntity.getKorisnicko_ime());
            korisnikDto.setIme(korinikEntity.getIme());
            korisnikDto.setPrezime(korinikEntity.getPrezime());
            korisnikDto.setOib(korinikEntity.getOib());
            korisnikDto.setEmail(korinikEntity.getEmail());
            korisnikDto.setTipKorisnika(korinikEntity.getTipKorisnika());
        }
        return korisnikDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM Korisnik e";
    }

}
