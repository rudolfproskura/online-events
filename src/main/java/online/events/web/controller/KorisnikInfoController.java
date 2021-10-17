package online.events.web.controller;


import online.events.bean.IKorisnikSessionBean;
import online.events.dao.KorisnikDao;
import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.util.DogadajAppConstants;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Web managed bean za dogadaj
 */

@Named
@ViewScoped
public class KorisnikInfoController implements Serializable {

    //fields
    private static final long serialVersionUID = 1L;

    private KorisnikDto korisnikInfoDto;

    private List<KorisnikDto> korisnikDtoList;
    private List<SelectItem> tipKorisnikaSelectItems = new ArrayList<>();


    //CDI
    @Inject
    private KorisnikDao korisnikDao;

    @EJB
    private IKorisnikSessionBean korisnikSessionBean;

    public KorisnikInfoController() {
        super();
    }

    @PostConstruct
    public void init() {
        getUserInfo(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }

    /*
     * save metoda za spremanje/ažuriranje događaja
     */
    public void getUserInfo(String userName) {
        try {
            korisnikInfoDto = korisnikDao.getKorisnikInfo(userName);
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom dohvaćanja podataka o korisniku.", DogadajAppConstants.SEVERITY_ERR);
        }
    }


    /*
     * save metoda za spremanje/ažuriranje događaja
     */
    public void editUser() {
        try {
            if (korisnikInfoDto != null) {
                korisnikSessionBean.editLDAPKorisnik(korisnikInfoDto);
                addMessage("Vaši podaci su promijenjeni", DogadajAppConstants.SEVERITY_INFO);
            } else {
                addMessage("Popunite Vaše podatke.", DogadajAppConstants.SEVERITY_WARN);

            }
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom promijene Vaših podataka.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    public void addMessage(String summary, String severity) {
        if (StringUtils.isNotBlank(summary) && StringUtils.isNotBlank(severity)) {
            FacesMessage message = null;
            switch (severity) {
                case DogadajAppConstants.SEVERITY_ERR:
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    break;
                case DogadajAppConstants.SEVERITY_INFO:
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
                    break;
                case DogadajAppConstants.SEVERITY_WARN:
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
                    break;
                default:
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }


    public KorisnikDto getKorisnikInfoDto() {
        return korisnikInfoDto;
    }

    public void setKorisnikInfoDto(KorisnikDto korisnikInfoDto) {
        this.korisnikInfoDto = korisnikInfoDto;
    }

    public List<KorisnikDto> getKorisnikDtoList() {
        return korisnikDtoList;
    }

    public void setKorisnikDtoList(List<KorisnikDto> korisnikDtoList) {
        this.korisnikDtoList = korisnikDtoList;
    }

    public List<SelectItem> getTipKorisnikaSelectItems() {
        return tipKorisnikaSelectItems;
    }

    public void setTipKorisnikaSelectItems(List<SelectItem> tipKorisnikaSelectItems) {
        this.tipKorisnikaSelectItems = tipKorisnikaSelectItems;
    }

}
