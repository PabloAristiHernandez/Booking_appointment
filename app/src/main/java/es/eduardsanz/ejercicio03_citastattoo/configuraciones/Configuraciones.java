package es.eduardsanz.ejercicio03_citastattoo.configuraciones;

import java.text.SimpleDateFormat;

public class Configuraciones {

    public static SimpleDateFormat simpleDateFormat;

    static {
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
}
