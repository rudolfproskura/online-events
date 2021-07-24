package online.events.util;


import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@FacesConverter(value = "LocalDateConverter")
public class LocalDateConverter extends DateTimeConverter {

    private static final String DEFAULT_DATE_TIME_FORMAT_TABLE_VIEW = "dd.MM.yyyy HH:mm";
    private static final String DEFAULT_DATE_TIME_FORMAT1 = "dd.MM.yyyy HH:mm";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LocalDateTime result = null;
        if (StringUtils.isNotBlank(value)) {
            result = LocalDateTime.parse(value, new DateTimeFormatterBuilder().appendPattern(component.getAttributes().getOrDefault("pattern", DEFAULT_DATE_TIME_FORMAT1).toString()).toFormatter());
        }
        return result;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value instanceof LocalDateTime) {
            LocalDateTime dateValue = (LocalDateTime) value;
            return dateValue.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT_TABLE_VIEW));
        }
        if(value instanceof LocalDate){
            LocalDate dateValue = (LocalDate) value;
            return dateValue.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT1));
        }
        return null;
    }


}
