package online.events.dao;

import online.events.dto.DogadajDto;
import online.events.dto.KorisnikDogadajDto;
import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.model.Dogadaj;
import online.events.model.Korisnik;
import online.events.model.KorisnikDogadaj;

import javax.persistence.Query;
import java.io.Serializable;

public class KorisnikDogadajDao extends GenericDao<Object, KorisnikDogadajDto> implements Serializable {

    @Override
    protected KorisnikDogadaj formEntity(KorisnikDogadajDto dto) {
        KorisnikDogadaj entity = null;
        if (dto != null) {
            entity = new KorisnikDogadaj();
            if (dto.getKorisnikDto() != null) {
                Korisnik korisnik = new Korisnik();
                korisnik.setKorisnicko_ime(dto.getKorisnikDto().getKorisnickoIme());
                entity.setKorisnik(korisnik);
            }
            if (dto.getDogadajDto() != null) {
                Dogadaj dogadaj = new Dogadaj();
                dogadaj.setSifraDogadaja(dto.getDogadajDto().getSifraDogadaja());
                entity.setDogadaj(dogadaj);
            }
        }
        return entity;
    }

    @Override
    protected KorisnikDogadajDto formDTO(Object o) {
        KorisnikDogadajDto korisnikDogadajDto = null;
        if (o != null) {
            korisnikDogadajDto = new KorisnikDogadajDto();
            if (o instanceof KorisnikDogadajDto) {
                KorisnikDogadaj korisnikDogadajEntity = (KorisnikDogadaj) o;
                if (korisnikDogadajEntity.getKorisnik() != null) {
                    KorisnikDto korisnikDto = new KorisnikDto();
                    korisnikDto.setKorisnickoIme(korisnikDogadajEntity.getKorisnik().getKorisnicko_ime());
                    korisnikDogadajDto.setKorisnikDto(korisnikDto);
                }
                if (korisnikDogadajEntity.getDogadaj() != null) {
                    DogadajDto dogadajDto = new DogadajDto();
                    dogadajDto.setSifraDogadaja(korisnikDogadajEntity.getDogadaj().getSifraDogadaja());
                    korisnikDogadajDto.setDogadajDto(dogadajDto);
                }
            }
        }

        return korisnikDogadajDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM KorisnikDogadaj e";
    }


    /**
     * Create new dogadaj entity
     *
     * @param dto
     * @return
     * @throws DogadajAppRuleException
     */
    public KorisnikDogadajDto create(KorisnikDogadajDto dto) throws DogadajAppRuleException {
        // TODO validateBeforeCreate(dto);
        KorisnikDogadaj entity = formEntity(dto);
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return formDTO(entity);
    }


    /**
     * Edit existing dogadaj entity
     *
     * @param korisnikDogadajDto
     * @throws DogadajAppRuleException
     */
    public void edit(KorisnikDogadajDto korisnikDogadajDto, String logedUser) throws DogadajAppRuleException {
        // TODO validateBeforeEdit(dogadajDto, logedUser);
        KorisnikDogadaj entity = formEntity(korisnikDogadajDto);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void createKorisnikDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {
        String sql = "insert into online_events.korisnik_dogadaj values (:korisnik, :dogadaj)";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("korisnik", korisnik);
        query.setParameter("dogadaj", dogadaj);
        query.executeUpdate();
    }

    public void deleteKorisnikDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {
        String sql = "delete from online_events.korisnik_dogadaj where korisnik = :korisnik and dogadaj=:dogadaj";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("korisnik", korisnik);
        query.setParameter("dogadaj", dogadaj);
        query.executeUpdate();
    }

    public void deleteKorisnikDogadaj(Integer dogadaj) throws DogadajAppRuleException {
        String sql = "delete from online_events.korisnik_dogadaj where dogadaj=:dogadaj";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("dogadaj", dogadaj);
        query.executeUpdate();
    }

    public void deleteKorisnikDogadajByKorisnik(String korisnik) throws DogadajAppRuleException {
        String sql = "delete from online_events.korisnik_dogadaj where korisnik = :korisnik";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("korisnik", korisnik);
        query.executeUpdate();
    }

    public void deleteKorisnikDogadajByKreator(String korisnik) throws DogadajAppRuleException {

        String sql = "delete from online_events.korisnik_dogadaj where dogadaj in " +
                        "(select sifra from online_events.dogadaj where kreator = :kreator) ";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("kreator", korisnik);
        query.executeUpdate();
    }

    public void deleteDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {

        deleteKorisnikDogadaj(dogadaj);

        String sql = "delete from online_events.dogadaj where sifra = :dogadaj";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("dogadaj", dogadaj);
        query.executeUpdate();
    }

    public void deleteDogadajByCreator(String kreator) throws DogadajAppRuleException {

        String sql = "delete from online_events.dogadaj where kreator = :kreator";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("kreator", kreator);
        query.executeUpdate();
    }

}
