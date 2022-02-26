package online.events.web.controller;


import online.events.bean.IDogadajSessionBean;
import online.events.dao.DogadajDao;
import online.events.dao.GradDao;
import online.events.dao.OrganizacijskaJedinicaDao;
import online.events.dao.VelicinaGradaDao;
import online.events.dto.*;
import online.events.exception.DogadajAppRuleException;
import online.events.util.DogadajAppConstants;
import online.events.util.DogadajAppUtil;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Web managed bean za dogadaj
 */

@Named
@ViewScoped
public class KalendarController implements Serializable {

    //fields
    private static final long serialVersionUID = 1L;
    private String logedUser;
    private DogadajDto dogadajDto;

    //za kalendar
    private ScheduleModel eventModelMyEvents;
    private ScheduleModel eventModelAllEvents;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    private List<DogadajDto> dogadajiAllList;
    private List<DogadajDto> dogadajiMyList;


    private boolean slotEventOverlap = true;
    private boolean showWeekNumbers = false;
    private boolean showHeader = true;
    private boolean draggable = true;
    private boolean resizable = true;
    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;
    private boolean rtl = false;

    private double aspectRatio = Double.MIN_VALUE;

    private String leftHeaderTemplate = "prev,next today";
    private String centerHeaderTemplate = "title";
    private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
    private String nextDayThreshold = "09:00:00";
    private String weekNumberCalculation = "local";
    private String weekNumberCalculator = "date.getTime()";
    private String displayEventEnd;
    private String timeFormat;
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String slotLabelFormat;
    private String scrollTime = "06:00:00";
    private String minTime = "04:00:00";
    private String maxTime = "20:00:00";
    private String locale = "en";
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";
    private String view = "timeGridWeek";
    private String height = "auto";

    private String extenderCode = "// Write your code here or select an example from above";
    private String selectedExtenderExample = "";

    //CDI
    @Inject
    private DogadajDao dogadajDao;

    //EJB
    @EJB
    private IDogadajSessionBean dogadajSessionBean;

    public KalendarController() {
        super();
    }

    @PostConstruct
    public void init() {
        //calendar
        getAllDogadaj();
    }

    private void getAllDogadaj() {

        try {
            eventModelAllEvents = new DefaultScheduleModel();
            getListAllDogadaj();
            getListMyDogadaj();
            if (dogadajiAllList != null && !dogadajiAllList.isEmpty()) {
                for (DogadajDto dogadajDto : dogadajiAllList) {
                    if (dogadajiMyList != null && !dogadajiMyList.isEmpty() && containsDogadajById(dogadajiMyList, dogadajDto.getSifraDogadaja())) {
                        DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                                .title(dogadajDto.getNazivDogadaja())
                                .startDate(dogadajDto.getVrijemeOd())
                                .endDate(dogadajDto.getVrijemeDo())
                                .description(dogadajDto.getGradDogadajaDto().getNazivGrada())
                               // .borderColor("GREEN")
                               // .textColor("GREEN")
                               // .backgroundColor("YELLOW")
                                .styleClass("myclass")
                                .overlapAllowed(true)
                                .build();
event.setStyleClass("myclass");
                        eventModelAllEvents.addEvent(event);
                    } else {
                        DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                                .title(dogadajDto.getNazivDogadaja())
                                .startDate(dogadajDto.getVrijemeOd())
                                .endDate(dogadajDto.getVrijemeDo())
                                .description(dogadajDto.getGradDogadajaDto().getNazivGrada())
                                //.borderColor("WHITE")
                                .overlapAllowed(true)
                                .build();
                        eventModelAllEvents.addEvent(event);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom pretraživanja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }

    }

    public boolean containsDogadajById(final List<DogadajDto> list, final Integer id){
        return list.stream().filter(o -> o.getSifraDogadaja().equals(id)).findFirst().isPresent();
    }

    private void getListAllDogadaj() {
        try {
            dogadajiAllList = dogadajDao.getFilterList(new DogadajFilterDto());
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom pretraživanja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }


    private void getListMyDogadaj() {
        try {
            DogadajFilterDto dogadajFilterDto = new DogadajFilterDto();
            dogadajFilterDto.setDodaniUKalendar("ADDED_TO_CAL");
            dogadajFilterDto.setLoggedUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            dogadajiMyList = dogadajDao.getFilterList(dogadajFilterDto);
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom pretraživanja događaja.", DogadajAppConstants.SEVERITY_ERR);
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



    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    //getters & setters
    public DogadajDto getDogadajDto() {
        return dogadajDto;
    }

    public void setDogadajDto(DogadajDto dogadajDto) {
        this.dogadajDto = dogadajDto;
    }

    public String getLogedUser() {
        return logedUser;
    }

    public void setLogedUser(String logedUser) {
        this.logedUser = logedUser;
    }

    public ScheduleModel getEventModelMyEvents() {
        return eventModelMyEvents;
    }

    public void setEventModelMyEvents(ScheduleModel eventModelMyEvents) {
        this.eventModelMyEvents = eventModelMyEvents;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public ScheduleModel getEventModelAllEvents() {
        return eventModelAllEvents;
    }

    public void setEventModelAllEvents(ScheduleModel eventModelAllEvents) {
        this.eventModelAllEvents = eventModelAllEvents;
    }

}
