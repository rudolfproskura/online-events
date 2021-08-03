package online.events.dao;


import online.events.dto.*;
import online.events.exception.DogadajAppRuleException;
import online.events.model.Dogadaj;
import online.events.model.Grad;
import online.events.model.Korisnik;
import online.events.util.DogadajAppConstants;
import online.events.util.DogadajAppUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DogadajDao extends GenericDao<Object, DogadajDto> implements Serializable {

    private static final long serialVersionUID = 1L;
    // indexes

    private static final int IDX_DOGADAJ_SIFRA = 0;
    private static final int IDX_DOGADAJ_NAZIV = 1;
    private static final int IDX_DOGADAJ_VRIJEME_OD = 2;
    private static final int IDX_DOGADAJ_VRIJEME_DO = 3;
    private static final int IDX_DOGADAJ_SLOBODAN_ULAZ = 4;
    private static final int IDX_GRAD_NAZIV = 5;
    private static final int IDX_GRAD_VELICINA_NAZIV = 6;
    private static final int IDX_ZUPANIJA_NAZIV = 7;
    private static final int IDX_REGIJA_NAZIV = 8;
    private static final int IDX_GRAD_SIFRA = 9;
    private static final int IDX_KREATOR_USERNAME = 10;


    @Override
    protected Dogadaj formEntity(DogadajDto dto) {
        Dogadaj entity = null;
        if (dto != null) {
            entity = new Dogadaj();
            entity.setSifraDogadaja(dto.getSifraDogadaja());
            entity.setNazivDogadaja(dto.getNazivDogadaja());
            entity.setVrijemeOd(dto.getVrijemeOd());
            entity.setVrijemeDo(dto.getVrijemeDo());
            if (dto.getGradDogadajaDto() != null) {
                Grad grad = new Grad();
                grad.setSifraGrada(dto.getGradDogadajaDto().getSifraGrada());
                entity.setGrad(grad);
            }
            if (StringUtils.isNotBlank(dto.getSlobodanUlaz())) {
                if (dto.getSlobodanUlaz().equals(DogadajAppConstants.DTO_SLOBODAN_ULAZ_FALSE) || dto.getSlobodanUlaz().equals(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE)) {
                    entity.setSlobodanUlaz(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE);
                }
                if (dto.getSlobodanUlaz().equals(DogadajAppConstants.DTO_SLOBODAN_ULAZ_TRUE) || dto.getSlobodanUlaz().equals(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA)) {
                    entity.setSlobodanUlaz(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA);
                }
            }
            if (dto.getKreatorDogadaja() != null) {
                Korisnik korisnik = new Korisnik();
                korisnik.setKorisnicko_ime(dto.getKreatorDogadaja().getKorisnickoIme());
                entity.setKreator(korisnik);
            }
        }
        return entity;
    }

    @Override
    protected DogadajDto formDTO(Object o) {
        DogadajDto dogadajDto = null;
        if (o != null) {
            dogadajDto = new DogadajDto();
            if (o instanceof Dogadaj) {
                Dogadaj dogadajEntity = (Dogadaj) o;
                dogadajDto.setSifraDogadaja(dogadajEntity.getSifraDogadaja());
                dogadajDto.setNazivDogadaja(dogadajEntity.getNazivDogadaja());
                dogadajDto.setVrijemeOd(dogadajEntity.getVrijemeOd());
                dogadajDto.setVrijemeDo(dogadajEntity.getVrijemeDo());
                dogadajDto.setSlobodanUlaz(dogadajEntity.getSlobodanUlaz());
                //kreator dogadaja
                if (dogadajEntity.getKreator() != null) {
                    KorisnikDto korisnikDto = new KorisnikDto();
                    korisnikDto.setKorisnickoIme(dogadajEntity.getKreator().getKorisnicko_ime());
                }
                //grad dogadaja
                if (dogadajEntity.getGrad() != null) {
                    GradDto gradDto = new GradDto();

                    gradDto.setSifraGrada(dogadajEntity.getGrad().getSifraGrada());
                    gradDto.setNazivGrada(dogadajEntity.getGrad().getNazivGrada());
                    //org jedinica grada
                    if (dogadajEntity.getGrad().getOrganizacijskaJedinica() != null) {
                        OrganizacijskaJedinicaDto organizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
                        organizacijskaJedinicaDto.setSifraOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getSifraOrgJedinice());
                        organizacijskaJedinicaDto.setNazivOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getNazivOrgJedinice());
                        organizacijskaJedinicaDto.setOpisOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getOpisOrgJedinice());
                        //tip organizacijske jedinice grada
                        TipOrganizacijskeJediniceDto tipOrganizacijskeJediniceDto = new TipOrganizacijskeJediniceDto();
                        tipOrganizacijskeJediniceDto.setNazivOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getTipOrganizacijskeJedinice().getNazivTipOrgJedinice());
                        tipOrganizacijskeJediniceDto.setSifraTipOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getTipOrganizacijskeJedinice().getSifraTipOrgJedinice());
                        organizacijskaJedinicaDto.setTipOrganizacijskeJediniceDto(tipOrganizacijskeJediniceDto);
                        //nadredena organizacijska jedinica organizacijske jedinice
                        if (dogadajEntity.getGrad().getOrganizacijskaJedinica().getNadredenaOrgJedinica() != null) {
                            OrganizacijskaJedinicaDto nadredenaOrganizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
                            nadredenaOrganizacijskaJedinicaDto.setSifraOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getNadredenaOrgJedinica().getSifraOrgJedinice());
                            nadredenaOrganizacijskaJedinicaDto.setNazivOrgJedinice(dogadajEntity.getGrad().getOrganizacijskaJedinica().getNadredenaOrgJedinica().getNazivOrgJedinice());
                            organizacijskaJedinicaDto.setNadredenaOrganizacijeDto(nadredenaOrganizacijskaJedinicaDto);
                        }
                        gradDto.setOrganizacijskaJedinicaDto(organizacijskaJedinicaDto);
                        //velicina grada
                        VelicinaGradaDto velicinaGradaDto = new VelicinaGradaDto();
                        velicinaGradaDto.setNazivVelicineGrada(dogadajEntity.getGrad().getVelicinaGrada().getNazivVelicineGrada());
                        velicinaGradaDto.setSifraVelicineGrada(dogadajEntity.getGrad().getVelicinaGrada().getSifraVelicineGrada());
                        gradDto.setVelicinaGradaDto(velicinaGradaDto);
                        dogadajDto.setGradDogadajaDto(gradDto);
                    }
                }

            } else {
                Object[] entity = (Object[]) o;
                dogadajDto.setSifraDogadaja(((BigInteger) entity[IDX_DOGADAJ_SIFRA]).intValue());
                dogadajDto.setNazivDogadaja((String) entity[IDX_DOGADAJ_NAZIV]);
                dogadajDto.setVrijemeOd(((Timestamp) entity[IDX_DOGADAJ_VRIJEME_OD]).toLocalDateTime());
                if (entity[IDX_DOGADAJ_VRIJEME_DO] != null)
                    dogadajDto.setVrijemeDo(((Timestamp) entity[IDX_DOGADAJ_VRIJEME_DO]).toLocalDateTime());
                dogadajDto.setSlobodanUlaz((String) entity[IDX_DOGADAJ_SLOBODAN_ULAZ]);
                //grad
                GradDto gradDto = new GradDto();
                gradDto.setNazivGrada((String) entity[IDX_GRAD_NAZIV]);
                gradDto.setSifraGrada(((BigInteger) entity[IDX_GRAD_SIFRA]).intValue());
                //velicina grada
                VelicinaGradaDto velicinaGradaDto = new VelicinaGradaDto();
                velicinaGradaDto.setNazivVelicineGrada((String) entity[IDX_GRAD_VELICINA_NAZIV]);
                gradDto.setVelicinaGradaDto(velicinaGradaDto);
                //zupanija
                OrganizacijskaJedinicaDto organizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
                organizacijskaJedinicaDto.setNazivOrgJedinice((String) entity[IDX_ZUPANIJA_NAZIV]);
                //regija
                OrganizacijskaJedinicaDto nadredenaOrganizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
                nadredenaOrganizacijskaJedinicaDto.setNazivOrgJedinice((String) entity[IDX_REGIJA_NAZIV]);
                organizacijskaJedinicaDto.setNadredenaOrganizacijeDto(nadredenaOrganizacijskaJedinicaDto);
                gradDto.setOrganizacijskaJedinicaDto(organizacijskaJedinicaDto);
                dogadajDto.setGradDogadajaDto(gradDto);
                //kreator
                KorisnikDto kreatorDogadajaDto = new KorisnikDto();
                kreatorDogadajaDto.setKorisnickoIme((String) entity[IDX_KREATOR_USERNAME]);
                dogadajDto.setKreatorDogadaja(kreatorDogadajaDto);
            }
        }
        return dogadajDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM Dogadaj e";
    }

    private void validateBeforeCreate(DogadajDto dogadajDto) throws DogadajAppRuleException {
        boolean hasError = false;
        List<String> messages = new ArrayList<String>();

        if (dogadajDto == null) {
            hasError = true;
            messages.add("Događaj nema podatke!");
        }
        if (StringUtils.isBlank(dogadajDto.getNazivDogadaja())) {
            hasError = true;
            messages.add("Događaj nema popunjen naziv!");
        }
        if (dogadajDto.getVrijemeOd() == null) {
            hasError = true;
            messages.add("Događaj nema odabrano vrijeme od!");
        }
        if (dogadajDto.getVrijemeOd() != null && dogadajDto.getVrijemeOd().isBefore(LocalDateTime.now())) {
            hasError = true;
            messages.add("Događaj ne može biti u proplosti!");
        }
        if (dogadajDto.getVrijemeOd() != null && dogadajDto.getVrijemeDo() != null && dogadajDto.getVrijemeDo().isBefore(dogadajDto.getVrijemeOd())) {
            hasError = true;
            messages.add("Događaj ne može završiti prije nego je započeo!");
        }
        if (dogadajDto.getGradDogadajaDto() == null || dogadajDto.getGradDogadajaDto().getSifraGrada() == null) {
            hasError = true;
            messages.add("Događaj nama odabran grad!");
        }
        if (StringUtils.isBlank(dogadajDto.getSlobodanUlaz())) {
            hasError = true;
            messages.add("Događaj nema odabran način ulaza!");
        }
        if (dogadajDto.getKreatorDogadaja() == null || StringUtils.isBlank(dogadajDto.getKreatorDogadaja().getKorisnickoIme())) {
            hasError = true;
            messages.add("Događaj nema unesenog kreatora!");
        }
        if (hasError && !messages.isEmpty()) {
            throw new DogadajAppRuleException(messages);
        }
    }

    private void validateBeforeEdit(DogadajDto dogadajDto) throws DogadajAppRuleException {
        boolean hasError = false;
        List<String> messages = new ArrayList<String>();

        if (dogadajDto == null) {
            hasError = true;
            messages.add("Događaj nema podatke!");
        }
        if (dogadajDto.getSifraDogadaja() == null) {
            hasError = true;
            messages.add("Događaj nema šifra - pokuavate ažurirati nepostojeći događaj!");
        }
        if (StringUtils.isBlank(dogadajDto.getNazivDogadaja())) {
            hasError = true;
            messages.add("Događaj nema popunjen naziv!");
        }
        if (dogadajDto.getVrijemeOd() == null) {
            hasError = true;
            messages.add("Događaj nema odabrano vrijeme od!");
        }
        if (dogadajDto.getVrijemeOd() != null && dogadajDto.getVrijemeOd().isBefore(LocalDateTime.now())) {
            hasError = true;
            messages.add("Događaj ne može biti u prošlosti!");
        }
        if (dogadajDto.getVrijemeOd() != null && dogadajDto.getVrijemeDo() != null && dogadajDto.getVrijemeDo().isBefore(dogadajDto.getVrijemeOd())) {
            hasError = true;
            messages.add("Događaj ne može završiti prije nego je započeo!");
        }
        if (dogadajDto.getGradDogadajaDto() == null || dogadajDto.getGradDogadajaDto().getSifraGrada() == null) {
            hasError = true;
            messages.add("Događaj nama odabran grad!");
        }
        if (StringUtils.isBlank(dogadajDto.getSlobodanUlaz())) {
            hasError = true;
            messages.add("Događaj nema odabran način ulaza!");
        }
        if (hasError && !messages.isEmpty()) {
            throw new DogadajAppRuleException(messages);
        }
    }

    /**
     * Create new dogadaj entity
     *
     * @param dto
     * @return
     * @throws DogadajAppRuleException
     */
    public DogadajDto create(DogadajDto dto) throws DogadajAppRuleException {
        validateBeforeCreate(dto);
        Dogadaj entity = formEntity(dto);
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return formDTO(entity);
    }

    /**
     * Edit existing dogadaj entity
     *
     * @param dogadajDto
     * @throws DogadajAppRuleException
     */
    public void edit(DogadajDto dogadajDto) throws DogadajAppRuleException {
        validateBeforeEdit(dogadajDto);
        Dogadaj entity = formEntity(dogadajDto);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    /**
     * Get filter list
     *
     * @param filterDto
     * @throws DogadajAppRuleException
     */
    public List<DogadajDto> getFilterList(DogadajFilterDto filterDto) throws DogadajAppRuleException {
        List<DogadajDto> resultList;

        //provjera
        if (filterDto == null) {
            throw new DogadajAppRuleException(new ArrayList<>(Arrays.asList("Filter je prazan.")));
        }
        //formiranje sql upita i izvršavanje sql upita
        List<Object[]> listDogadajObjects = formAndExecuteFilterSql(filterDto);
        //formiranje liste dogadaja
        if (listDogadajObjects != null && !listDogadajObjects.isEmpty()) {
            resultList = new ArrayList<>();
            listDogadajObjects.stream().forEach(p -> resultList.add(formDTO(p)));
        } else {
            resultList = null;
        }

        return resultList;
    }

    private List<Object[]> formAndExecuteFilterSql(DogadajFilterDto filterDto) {
        List<Object[]> resultList = null;

        String sql = "select dog.sifra, dog.naziv, dog.vrijeme_od, dog.vrijeme_do, dog.slobodan_ulaz, grad.naziv as nazivg, vel_gr.naziv velicinag , org_jed.naziv nazivz, nad_org_jed.naziv nazivr, grad.sifra as sifrag, dog.kreator from online_events.dogadaj dog " +
                "join online_events.grad grad on grad.sifra = dog.grad " +
                "join online_events.velicina_grada vel_gr on vel_gr.sifra = grad.velicina " +
                "join online_events.organizacijska_jedinica org_jed on org_jed.sifra = grad.org_jedinica " +
                "join online_events.organizacijska_jedinica nad_org_jed on nad_org_jed.sifra = org_jed.org_jedinica ";
        if (filterDto.getKorisnik() != null)
            sql = sql + "join online_events.korisnik_dogadaj korisnik_dogadaj on korisnik_dogadaj.dogadaj = dog.sifra ";
        sql = sql + "where 1 = 1 ";
        //where dio
        if (filterDto.getSifraDogadaja() != null) sql = sql + "and dog.sifra = :sifraDogadaja ";
        if (StringUtils.isNotBlank(filterDto.getNazivDogadaja())) sql = sql + "and dog.naziv like :nazivDogadaja ";
        if (filterDto.getVrijemeOdPocetak() != null) sql = sql + "and dog.vrijeme_od >= :vrijemeOdPocetak ";
        if (filterDto.getVrijemeDoPocetak() != null) sql = sql + "and dog.vrijeme_od <= :vrijemeDoPocetak ";
        if (filterDto.getVrijemeOdKraj() != null) sql = sql + "and dog.vrijeme_do >= :vrijemeOdKraj ";
        if (filterDto.getVrijemeDoKraj() != null) sql = sql + "and dog.vrijeme_do <= :vrijemeDoKraj ";
        if (StringUtils.isNotBlank(filterDto.getSlobodanUlaz())) sql = sql + "and dog.slobodan_ulaz = :ulazSlobodan ";
        if (filterDto.getOdabraneRegije() != null && filterDto.getOdabraneRegije().length > 0)
            sql = sql + "and nad_org_jed.sifra in :regije ";
        if (filterDto.getOdabraneZupanije() != null && filterDto.getOdabraneZupanije().length > 0)
            sql = sql + "and org_jed.sifra in :zupanije ";
        if (filterDto.getOdabraneVelicineGrada() != null && filterDto.getOdabraneVelicineGrada().length > 0)
            sql = sql + "and vel_gr.sifra in :velicineGrada ";
        if (filterDto.getOdabraniGradovi() != null && filterDto.getOdabraniGradovi().length > 0)
            sql = sql + "and grad.sifra in :gradovi ";
        if (filterDto.getKorisnik() != null)
            sql = sql + "and korisnik_dogadaj.korisnik = :korisnik ";
        //default order by
        sql = sql + " order by dog.sifra ";
        Query queryDogadaj = getEntityManager().createNativeQuery(sql);
        //parametri
        if (filterDto.getSifraDogadaja() != null)
            queryDogadaj.setParameter("sifraDogadaja", filterDto.getSifraDogadaja());
        if (StringUtils.isNotBlank(filterDto.getNazivDogadaja()))
            queryDogadaj.setParameter("nazivDogadaja", "%" + filterDto.getNazivDogadaja() + "%");
        if (filterDto.getVrijemeOdPocetak() != null)
            queryDogadaj.setParameter("vrijemeOdPocetak", filterDto.getVrijemeOdPocetak());
        if (filterDto.getVrijemeDoPocetak() != null)
            queryDogadaj.setParameter("vrijemeDoPocetak", filterDto.getVrijemeDoPocetak());
        if (filterDto.getVrijemeOdKraj() != null)
            queryDogadaj.setParameter("vrijemeOdKraj", filterDto.getVrijemeOdKraj());
        if (filterDto.getVrijemeDoKraj() != null)
            queryDogadaj.setParameter("vrijemeDoKraj", filterDto.getVrijemeDoKraj());
        if (StringUtils.isNotBlank(filterDto.getSlobodanUlaz()))
            queryDogadaj.setParameter("ulazSlobodan", filterDto.getSlobodanUlaz());
        if (filterDto.getOdabraneRegije() != null && filterDto.getOdabraneRegije().length > 0)
            queryDogadaj.setParameter("regije", DogadajAppUtil.getIntegerFromStringList(filterDto.getOdabraneRegije()));
        if (filterDto.getOdabraneZupanije() != null && filterDto.getOdabraneZupanije().length > 0)
            queryDogadaj.setParameter("zupanije", DogadajAppUtil.getIntegerFromStringList(filterDto.getOdabraneZupanije()));
        if (filterDto.getOdabraneVelicineGrada() != null && filterDto.getOdabraneVelicineGrada().length > 0)
            queryDogadaj.setParameter("velicineGrada", DogadajAppUtil.getIntegerFromStringList(filterDto.getOdabraneVelicineGrada()));
        if (filterDto.getOdabraniGradovi() != null && filterDto.getOdabraniGradovi().length > 0)
            queryDogadaj.setParameter("gradovi", DogadajAppUtil.getIntegerFromStringList(filterDto.getOdabraniGradovi()));

        //aktivni (pretposatvio da se smatra da je grad aktivan ako mu je velicina aktivna
        if (filterDto.getSifraDogadaja() == null && (filterDto.getOdabraneRegije() == null || filterDto.getOdabraneRegije().length == 0) && (filterDto.getOdabraneZupanije() == null || filterDto.getOdabraneZupanije().length == 0) && (filterDto.getOdabraniGradovi() == null || filterDto.getOdabraniGradovi().length == 0)) {
            sql = sql + " and vel_gr.aktivan = true ";
        }
        if (filterDto.getKorisnik() != null)
            queryDogadaj.setParameter("korisnik", filterDto.getKorisnik());

        //izvrši query
        resultList = queryDogadaj.getResultList();
        return resultList;
    }

}
