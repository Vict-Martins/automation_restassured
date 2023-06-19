package br.ce.wcaquino.rest.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static java.util.Calendar.*;

public class DataUtils {

    public static String getDataDifDiasInteger(Integer qDias) {

        Calendar cal = getInstance();
        cal.add(Calendar.DAY_OF_MONTH, qDias);
        return getDataFormatada(cal.getTime());

    }

    public static String getDataFormatada(Date data) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(data);
    }

}
