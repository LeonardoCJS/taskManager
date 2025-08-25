package org.example.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//classe utilitaria
public class DateUtil {

    //Data esperada pelo input
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate converterData(String dataEntregaInputUsuario) {
        return LocalDate.parse(dataEntregaInputUsuario,DATE_TIME_FORMATTER);
    }
}
