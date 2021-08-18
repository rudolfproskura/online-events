package online.events.web.controller;


import online.events.bean.IDogadajSessionBean;
import online.events.dao.KorisnikDao;
import online.events.dao.VelicinaGradaDao;
import online.events.dto.KorisnikDto;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/*
 * Web managed bean za dogadaj
 */

@Named
@ViewScoped
public class PregledKorisnikaController implements Serializable {

    //fields
    private static final long serialVersionUID = 1L;

    private List<KorisnikDto> korisnikDtoList;

    //CDI
    @Inject
    private KorisnikDao korisnikDao;
    ;

    public PregledKorisnikaController() {
        super();
    }

    @PostConstruct
    public void init() {
        korisnikDtoList = korisnikDao.findAll();
    }

    public List<KorisnikDto> getKorisnikDtoList() {
        return korisnikDtoList;
    }

    public void setKorisnikDtoList(List<KorisnikDto> korisnikDtoList) {
        this.korisnikDtoList = korisnikDtoList;
    }
}
