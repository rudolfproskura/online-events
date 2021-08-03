package online.events.bean;



import online.events.dto.DogadajDto;
import online.events.exception.DogadajAppRuleException;

import javax.ejb.Local;

@Local
public interface IDogadajSessionBean {

	/**
	 * <p>create dogadaj</p>
	 * @param dogadajDto
	 * @return dogadajDto
	 * @throws DogadajAppRuleException
	 */
	public DogadajDto createDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException;

	/**
	 * <p>edit dogadaj</p>
	 * @param dogadajDto
	 * @return
	 * @throws DogadajAppRuleException
	 */
	public void editDogadaj(DogadajDto dogadajDto, String logedUser) throws DogadajAppRuleException;


}
