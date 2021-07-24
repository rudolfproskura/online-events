package online.events.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogadajAppUtil {

    public static List<Integer> getIntegerFromStringList(String[] strings) {
        List<Integer> resultList = null;
        if (strings.length > 0) {
            resultList = new ArrayList<>();
            List<String> listRegije = Arrays.asList(strings);
            for (String s : listRegije) resultList.add(Integer.valueOf(s));
        }
        return resultList;
    }
}
