package online.events.bean;


import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;

import javax.ejb.Local;

@Local
public interface IKorisnikSessionBean {

	/**
	 * <p>create dogadaj</p>
	 * @param korisnikDto
	 * @return korisnikDto
	 * @throws DogadajAppRuleException
	 */
	public KorisnikDto createEditKorisnik(KorisnikDto korisnikDto) throws DogadajAppRuleException;


	}
