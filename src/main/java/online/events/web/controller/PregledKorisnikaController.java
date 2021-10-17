package online.events.web.controller;


import online.events.bean.IKorisnikSessionBean;
import online.events.dao.KorisnikDao;
import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.util.DogadajAppConstants;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Web managed bean za dogadaj
 */

@Named
@ViewScoped
public class PregledKorisnikaController implements Serializable {

    //fields
    private static final long serialVersionUID = 1L;

    private KorisnikDto korisnikDto;
    private KorisnikDto korisnikFilterDto;

    private List<KorisnikDto> korisnikDtoList;
    private List<SelectItem> tipKorisnikaSelectItems = new ArrayList<>();

    private KorisnikDto korisnikInfoDto;


    //CDI
    @Inject
    private KorisnikDao korisnikDao;

    @EJB
    private IKorisnikSessionBean korisnikSessionBean;

    public PregledKorisnikaController() {
        super();
    }

    @PostConstruct
    public void init() {
        tipKorisnikaSelectItems.add(new SelectItem(null, ""));
        tipKorisnikaSelectItems.add(new SelectItem("user", "Registrirani korisnik"));
        tipKorisnikaSelectItems.add(new SelectItem("organizer", "Organizator"));
        tipKorisnikaSelectItems.add(new SelectItem("admin", "Administrator"));

        korisnikDto = new KorisnikDto();
        korisnikFilterDto = new KorisnikDto();

        korisnikDtoList = null;
        getUserInfo(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }

    /*
     * Resetiran dto - koristi se kod odabira "Novi" na input formi
     */
    public void resetDto() {
        getKorisnikDto().setKorisnickoIme(null);
        getKorisnikDto().setIme(null);
        getKorisnikDto().setPrezime(null);
        getKorisnikDto().setOib(null);
        getKorisnikDto().setEmail(null);
        getKorisnikDto().setTipKorisnika(null);
        resetFilterDto();
    }

    public void resetFilterDto() {
        getKorisnikFilterDto().setKorisnickoIme(null);
        getKorisnikFilterDto().setIme(null);
        getKorisnikFilterDto().setPrezime(null);
        getKorisnikFilterDto().setOib(null);
        getKorisnikFilterDto().setEmail(null);
        getKorisnikFilterDto().setTipKorisnika(null);
        korisnikDtoList = null;
    }

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
    public void save() {
        try {
            if (korisnikDto != null) {
                korisnikDto.setTipKorisnika("user");
                korisnikSessionBean.createEditKorisnik(korisnikDto);
                addMessage(korisnikDto.getIme() + " " + korisnikDto.getPrezime() + " uspješno ste se registrirali.", DogadajAppConstants.SEVERITY_INFO);
                korisnikDtoList = korisnikDao.getFilterList(korisnikDto);
            } else {
                addMessage("Korisnik je prazan (nema podataka).", DogadajAppConstants.SEVERITY_WARN);

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
            addMessage("Došlo je do greške prilikom kreiranja/ažuriranja korisnika.", DogadajAppConstants.SEVERITY_ERR);
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

    public void checkUserName() {
        try {
            if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme())) {
                korisnikDao.checkUserNameExsists(korisnikDto.getKorisnickoIme());
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
            korisnikDto.setKorisnickoIme("");
            addMessage("Došlo je do greške prilikom provjere korisničkog imena.", DogadajAppConstants.SEVERITY_ERR);
        }
    }


    public void save1() {
        try {
            if (korisnikDto != null) {
                korisnikSessionBean.createEditKorisnik(korisnikDto);
                addMessage("Korisnik " + korisnikDto.getKorisnickoIme() + " je uspješno spremljen.", DogadajAppConstants.SEVERITY_INFO);
                korisnikDtoList = korisnikDao.getFilterList(korisnikDto);
            } else {
                addMessage("Korisnik je prazan (nema podataka).", DogadajAppConstants.SEVERITY_WARN);

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
            addMessage("Došlo je do greške prilikom kreiranja/ažuriranja korisnika.", DogadajAppConstants.SEVERITY_ERR);
        }
    }


    /*
     * Dohvati korisnike prema popunjenom filteru
     */
    public void getFilterListKorisnik() {
        try {
            korisnikDtoList = korisnikDao.getFilterUsers(korisnikFilterDto);
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom pretraživanja korisnika.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    /*
     * Odabirom retk u tablici popunjava se dogadajDto, odnosno popunjava input form
     */
    public void onTableRowSelect(AjaxBehaviorEvent event) {
        try {
            Object selected = ((SelectEvent) event).getObject();
            if (selected instanceof KorisnikDto && ((KorisnikDto) selected).getKorisnickoIme() != null) {
                KorisnikDto selectedKorisnikDto = (KorisnikDto) selected;
                KorisnikDto korisnikFilterDto = new KorisnikDto();
                korisnikFilterDto.setKorisnickoIme(selectedKorisnikDto.getKorisnickoIme());
                korisnikDto = korisnikDao.getFilterList(korisnikFilterDto).get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške odabira događaja.", DogadajAppConstants.SEVERITY_ERR);
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

    public void openUserDetailDialog() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg3').show();");
    }

    public void openUserDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("welcome", options, null);
    }

    public KorisnikDto getKorisnikDto() {
        return korisnikDto;
    }

    public void setKorisnikDto(KorisnikDto korisnikDto) {
        this.korisnikDto = korisnikDto;
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

    public KorisnikDto getKorisnikFilterDto() {
        return korisnikFilterDto;
    }

    public void setKorisnikFilterDto(KorisnikDto korisnikFilterDto) {
        this.korisnikFilterDto = korisnikFilterDto;
    }

    public KorisnikDto getKorisnikInfoDto() {
        return korisnikInfoDto;
    }

    public void setKorisnikInfoDto(KorisnikDto korisnikInfoDto) {
        this.korisnikInfoDto = korisnikInfoDto;
    }
}
