package online.events.bean;

import online.events.dao.KorisnikDao;
import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KorisnikSessionBean implements IKorisnikSessionBean {

    @Inject
    private KorisnikDao korisnikDao;


    /**
     * <p>create dogadaj</p>
     *
     * @param korisnikDto
     * @return korisnikDto
     * @throws DogadajAppRuleException
     */
    public KorisnikDto createEditKorisnik(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        korisnikDao.validateBeforeCreate(korisnikDto);
        KorisnikDto korisnikFilter = new KorisnikDto();
        korisnikFilter.setKorisnickoIme(korisnikDto.getKorisnickoIme());
        List<KorisnikDto> korisnikFilterList = korisnikDao.getFilterList(korisnikFilter);
        if (korisnikFilterList != null && korisnikFilterList.size() > 0) {
            korisnikDao.edit(korisnikDto);
            return korisnikDto;
        } else {
            return korisnikDao.create(korisnikDto);
        }
    }

    /**
     * <p>create korisnik</p>
     *
     * @param korisnikDto
     * @return korisnikDto
     * @throws DogadajAppRuleException
     */
    public KorisnikDto createLDAPAndDBKorisnik(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        //validacija
        korisnikDao.validateBeforeCreate(korisnikDto);
        //ldap create user
        korisnikDao.insertLDAPUser(korisnikDto);

        korisnikDao.create(korisnikDto);
        return korisnikDto;
    }


}
