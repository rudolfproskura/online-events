package online.events.dao;

import online.events.dto.DogadajDto;
import online.events.dto.KorisnikDogadajDto;
import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.model.Dogadaj;
import online.events.model.Korisnik;
import online.events.model.KorisnikDogadaj;

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
        /*
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
                if (StringUtils.equals(dogadajDto.getSlobodanUlaz(), DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA)) {
                    dogadajDto.setSlobodanUlazBoolen(true);
                } else {
                    dogadajDto.setSlobodanUlazBoolen(false);
                }
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
                if (StringUtils.equals(dogadajDto.getSlobodanUlaz(), DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA)) {
                    dogadajDto.setSlobodanUlazBoolen(true);
                } else {
                    dogadajDto.setSlobodanUlazBoolen(false);
                }
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
         */
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

}
