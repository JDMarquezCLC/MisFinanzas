package com.josedavid.misfinanzas.otros.adaptadores.adaptadorfecha;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdaptadorFecha {

    public LocalDateTime convertirFecha(String fecha){
        String stringFecha = fecha;
        DateTimeFormatter dateTimeFormatter = null;
        String formato;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            formato = "yyyy-MM-dd HH:mm";
            dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
        }
        LocalDateTime fechaDevolver = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try{
                fechaDevolver = LocalDateTime.parse(stringFecha,dateTimeFormatter);
            } catch(DateTimeParseException e1){
                try{
                    formato = "yyyyMMdd hh:mm:ss a";
                    dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
                    fechaDevolver = LocalDateTime.parse(stringFecha,dateTimeFormatter);
                }catch(DateTimeParseException e2){
                    stringFecha = stringFecha.replace("PM","p. m.");
                    stringFecha = stringFecha.replace("AM","a. m.");
                    formato = "yyyyMMdd hh:mm:ss a";
                    dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
                    fechaDevolver = LocalDateTime.parse(stringFecha,dateTimeFormatter);
                }
            }

        }

        return fechaDevolver;

    }

}
