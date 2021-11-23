package online.events.bean;



import online.events.dao.DogadajDao;
import online.events.dao.KorisnikDao;
import online.events.dao.KorisnikDogadajDao;
import online.events.dto.DogadajDto;
import online.events.exception.DogadajAppRuleException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/*
 * Session bean for event app
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DogadajSessionBean implements IDogadajSessionBean {

    @Inject
    private DogadajDao dogadajDao;

    @Inject
    private KorisnikDao korisnikDao;

    @Inject
    private KorisnikDogadajDao korisnikDogadajDao;


    /**
     * <p>create dogadaj</p>
     * @param dogadajDto
     * @return dogadajDto
     * @throws DogadajAppRuleException
     */
    public DogadajDto createDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException {
        return dogadajDao.create(dogadajDto);
    }

    /**
     * <p>edit dogadaj</p>
     * @param dogadajDto
     * @return
     * @throws DogadajAppRuleException
     */
    public void editDogadaj(DogadajDto dogadajDto, String logedUser) throws DogadajAppRuleException {
        dogadajDao.edit(dogadajDto, logedUser);
    }

    public void createKorisnikDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {
        korisnikDogadajDao.createKorisnikDogadaj(korisnik, dogadaj);
    }

    public void deleteKorisnikDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {
        korisnikDogadajDao.deleteKorisnikDogadaj(korisnik, dogadaj);
    }

    public void deleteDogadaj(String korisnik, Integer dogadaj) throws DogadajAppRuleException {
        korisnikDogadajDao.deleteDogadaj(korisnik, dogadaj);
    }

    public void deleteKorisnik(String korisnik) throws DogadajAppRuleException {
        //DB delete dogadaj korisnik
        korisnikDogadajDao.deleteKorisnikDogadajByKorisnik(korisnik);
        //DB delete dogadaj
        korisnikDogadajDao.deleteDogadajByCreator(korisnik);
        //DB delete korisnik
        korisnikDao.deleteKorisnik(korisnik);
        //LDAP delete
        korisnikDao.deleteUserFromLDAP(korisnik);
    }


}
