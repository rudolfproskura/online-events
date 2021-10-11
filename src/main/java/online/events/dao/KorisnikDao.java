package online.events.dao;


import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.model.Korisnik;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KorisnikDao extends GenericDao<Object, KorisnikDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int IDX_KORISNIKA_KORISNICKO_IME = 0;
    private static final int IDX_KORISNIK_IME = 1;
    private static final int IDX_KORISNIK_PREZIME = 2;
    private static final int IDX_KORISNIK_OIB = 3;
    private static final int IDX_KORISNIK_EMAIL = 4;
    private static final int IDX_KORISNIK_TIP_KORISNIKA = 5;

    private static final String  validator = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+"
            + "(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";

    @Override
    protected Korisnik formEntity(KorisnikDto dto) {
        Korisnik entity = null;
        if (dto != null) {
            entity = new Korisnik();
            entity.setKorisnicko_ime(dto.getKorisnickoIme());
            entity.setIme(dto.getIme());
            entity.setPrezime(dto.getPrezime());
            entity.setOib(dto.getOib());
            entity.setEmail(dto.getEmail());
            entity.setTipKorisnika(dto.getTipKorisnika());
        }
        return entity;
    }

    @Override
    protected KorisnikDto formDTO(Object o) {
        KorisnikDto korisnikDto = null;
        if (o != null) {
            korisnikDto = new KorisnikDto();
            if (o instanceof Korisnik) {
                Korisnik korinikEntity = (Korisnik) o;
                korisnikDto.setKorisnickoIme(korinikEntity.getKorisnicko_ime());
                korisnikDto.setIme(korinikEntity.getIme());
                korisnikDto.setPrezime(korinikEntity.getPrezime());
                korisnikDto.setOib(korinikEntity.getOib());
                korisnikDto.setEmail(korinikEntity.getEmail());
                korisnikDto.setTipKorisnika(korinikEntity.getTipKorisnika());
            } else {
                Object[] entity = (Object[]) o;
                korisnikDto.setKorisnickoIme((String) entity[IDX_KORISNIKA_KORISNICKO_IME]);
                korisnikDto.setIme((String) entity[IDX_KORISNIK_IME]);
                korisnikDto.setPrezime((String) entity[IDX_KORISNIK_PREZIME]);
                korisnikDto.setOib((String) entity[IDX_KORISNIK_OIB]);
                korisnikDto.setEmail((String) entity[IDX_KORISNIK_EMAIL]);
                korisnikDto.setTipKorisnika((String) entity[IDX_KORISNIK_TIP_KORISNIKA]);
            }
        }
        return korisnikDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM Korisnik e";
    }

    public List<KorisnikDto> getFilterList(KorisnikDto filterDto) throws DogadajAppRuleException {
        List<KorisnikDto> resultList;

        //provjera
        if (filterDto == null) {
            throw new DogadajAppRuleException(new ArrayList<>(Arrays.asList("Filter je prazan.")));
        }
        //formiranje sql upita i izvršavanje sql upita
        List<Object[]> listKorisnikObjects = formAndExecuteFilterSql(filterDto);
        //formiranje liste dogadaja
        if (listKorisnikObjects != null && !listKorisnikObjects.isEmpty()) {
            resultList = new ArrayList<>();
            listKorisnikObjects.stream().forEach(p -> resultList.add(formDTO(p)));
        } else {
            resultList = null;
        }
        return resultList;
    }

    private List<Object[]> formAndExecuteFilterSql(KorisnikDto filterDto) {
        List<Object[]> resultList = null;

        String sql = "select korisnicko_ime, ime, prezime, oib, email, tip_korisnika  from online_events.korisnik ";
        ;
        sql = sql + "where 1 = 1 ";
        //where dio
        if (StringUtils.isNotBlank(filterDto.getKorisnickoIme())) sql = sql + "and korisnicko_ime = :korisnickoIme ";
        if (StringUtils.isNotBlank(filterDto.getIme())) sql = sql + "and ime = :ime ";
        if (StringUtils.isNotBlank(filterDto.getPrezime())) sql = sql + "and prezime = :prezime ";
        if (StringUtils.isNotBlank(filterDto.getOib())) sql = sql + "and oib = :oib ";
        if (StringUtils.isNotBlank(filterDto.getEmail())) sql = sql + "and email = :email ";
        if (StringUtils.isNotBlank(filterDto.getTipKorisnika())) sql = sql + "and tip_korisnika = :tipKorisnika ";

        //default order by
        sql = sql + " order by korisnicko_ime ";
        Query queryDogadaj = getEntityManager().createNativeQuery(sql);
        //parametri
        if (StringUtils.isNotBlank(filterDto.getKorisnickoIme()))
            queryDogadaj.setParameter("korisnickoIme", filterDto.getKorisnickoIme());
        if (StringUtils.isNotBlank(filterDto.getIme()))
            queryDogadaj.setParameter("ime", filterDto.getIme());
        if (StringUtils.isNotBlank(filterDto.getPrezime()))
            queryDogadaj.setParameter("prezime", filterDto.getPrezime());
        if (StringUtils.isNotBlank(filterDto.getOib()))
            queryDogadaj.setParameter("oib", filterDto.getOib());
        if (StringUtils.isNotBlank(filterDto.getEmail()))
            queryDogadaj.setParameter("email", filterDto.getEmail());
        if (StringUtils.isNotBlank(filterDto.getTipKorisnika()))
            queryDogadaj.setParameter("tipKorisnika", filterDto.getTipKorisnika());

        //izvrši query
        resultList = queryDogadaj.getResultList();
        return resultList;
    }

    /**
     * Create new dogadaj entity
     *
     * @param dto
     * @return
     * @throws DogadajAppRuleException
     */
    public KorisnikDto create(KorisnikDto dto) throws DogadajAppRuleException {
        validateBeforeCreate(dto);
        Korisnik entity = formEntity(dto);
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return formDTO(entity);
    }

    /**
     * Edit existing korisnik entity
     *
     * @param korisnikDto
     * @throws DogadajAppRuleException
     */
    public void edit(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        validateBeforeCreate(korisnikDto);
        Korisnik entity = formEntity(korisnikDto);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void validateBeforeCreate(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        boolean hasError = false;
        List<String> messages = new ArrayList<String>();

        if (korisnikDto == null) {
            hasError = true;
            messages.add("Korisnik nema podatke!");
        }
        if (StringUtils.isBlank(korisnikDto.getKorisnickoIme())) {
            hasError = true;
            messages.add("Korisničko ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && (korisnikDto.getKorisnickoIme().length() < 3 ||
                korisnikDto.getKorisnickoIme().length() > 20)) {
            hasError = true;
            messages.add("Korisničko ime mora imati minimalno 3 znaka, a maksimalno 20 znakova!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && korisnikDto.getKorisnickoIme().contains(" ")) {
            hasError = true;
            messages.add("Korisničko ime ne smije sadržavati razmak!");
        }
        if (StringUtils.isBlank(korisnikDto.getIme())) {
            hasError = true;
            messages.add("Ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getIme()) && (korisnikDto.getIme().length() < 2 ||
                korisnikDto.getIme().length() > 20)) {
            hasError = true;
            messages.add("Ime mora imati minimalno 2 znaka, a maksimalno 20 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getPrezime())) {
            hasError = true;
            messages.add("Prezime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getPrezime()) && (korisnikDto.getPrezime().length() < 2 ||
                korisnikDto.getPrezime().length() > 20)) {
            hasError = true;
            messages.add("Prezime mora imati minimalno 2 znaka, a maksimalno 20 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getOib())) {
            hasError = true;
            messages.add("OIB je obavezan podatak!");
        }
        if (StringUtils.isBlank(korisnikDto.getEmail())) {
            hasError = true;
            messages.add("Email je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getEmail()) && !korisnikDto.getEmail().matches(validator)) {
            hasError = true;
            messages.add("Neispravan email!");
        }
        if (StringUtils.isBlank(korisnikDto.getTipKorisnika())) {
            hasError = true;
            messages.add("Korisnik nema popunjenu ulogu!");
        }
        if (hasError && !messages.isEmpty()) {
            throw new DogadajAppRuleException(messages);
        }
    }


}
